package config.kafka.event;

import java.math.BigDecimal;

public record CollateralRiskPayload(
    String collateralPositionId,
    String loanId,
    String riskEventType,
    BigDecimal ltv,
    BigDecimal outstandingPrincipal,
    BigDecimal collateralMarketValue,
    String currency)
    implements EventPayload {}
