# syntax=docker/dockerfile:1

# ─── Stage 1: Dependency Cache ────────────────────────────────────────────────
FROM eclipse-temurin:25-jdk-alpine AS dependencies

WORKDIR /app

# Copy root and all module POM files for precise dependency caching
COPY pom.xml ./
COPY payguard-proto/pom.xml ./payguard-proto/
COPY payguard-api-gateway/pom.xml ./payguard-api-gateway/
COPY payguard-wallet-service/pom.xml ./payguard-wallet-service/
COPY payguard-loan-service/pom.xml ./payguard-loan-service/
COPY payguard-collateral-service/pom.xml ./payguard-collateral-service/

COPY --chmod=0755 mvnw ./mvnw
COPY .mvn/ .mvn/

# Download dependencies offline to leverage Docker layer caching
RUN ./mvnw --batch-mode --no-transfer-progress dependency:go-offline -B || true


# ─── Stage 2: Application Build ───────────────────────────────────────────────
FROM dependencies AS builder

WORKDIR /app

# Copy source code and configurations for all modules
COPY config/ ./config/
COPY payguard-proto/ ./payguard-proto/
COPY payguard-api-gateway/./payguard-api-gateway/
COPY payguard-wallet-service/ ./payguard-wallet-service/
COPY payguard-loan-service/ ./payguard-loan-service/
COPY payguard-collateral-service/ ./payguard-collateral-service/

# Receive service name via Build Arg (defaults to wallet service)
ARG SERVICE_NAME=payguard-wallet-service

# Build the target service package along with internal dependencies (-am) without running tests
RUN ./mvnw --batch-mode --no-transfer-progress clean package -pl :${SERVICE_NAME} -am -DskipTests


# ─── Stage 3: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine AS runtime

WORKDIR /app

# High security: Create a non-root system user and isolate the data directory
RUN addgroup -S payguard \
    && adduser -S payguard -G payguard \
    && mkdir -p /var/lib/payguard \
    && chown -R payguard:payguard /var/lib/payguard

ARG SERVICE_NAME=payguard-wallet-service

# Copy the output JAR file from the build stage to the lightweight JRE runtime image
COPY --from=builder --chown=payguard:payguard /app/${SERVICE_NAME}/target/*.jar ./app.jar

# Run the container as a non-privileged user
USER payguard

EXPOSE 8080

# Java 25 runtime optimizations
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]