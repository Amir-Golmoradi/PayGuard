package config.kafka.consumer;

import java.util.UUID;

/**
 * Service-owned atomic deduplication store. Implement with a unique database
 * constraint on (eventId, consumerName). The marker insert and business update
 * must commit in the SAME local database transaction. Return false for a
 * duplicate without leaving the transaction rollback-only.
 */
public interface ProcessedEventStore {
   boolean tryMarkProcessed(UUID eventId, String consumerName);
}
