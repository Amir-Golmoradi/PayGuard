package config.kafka.consumer;

import java.util.UUID;

/** Service-owned store for atomically recording processed event and consumer pairs. */
@FunctionalInterface
public interface ProcessedEventStore {
  boolean tryMarkProcessed(UUID eventId, String consumerName);
}
