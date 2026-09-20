package config.kafka.event;

public record EventMetadata(
        String correlationId,
        String causationId,
        String initiatedBy
) {
}