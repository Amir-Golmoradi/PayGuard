package config.kafka.consumer;

import config.kafka.event.EventPayload;
import config.kafka.event.PayGuardEvent;
import java.util.Objects;

/** Coordinates event deduplication with the consumer's local transaction. */
public final class IdempotentEventProcessor {
  private final ProcessedEventStore eventStore;

  public IdempotentEventProcessor(ProcessedEventStore eventStore) {
    this.eventStore = Objects.requireNonNull(eventStore, "eventStore must not be null");
  }

  /** Returns true when the handler ran, or false when the event was already processed. */
  public boolean process(
      PayGuardEvent<? extends EventPayload> event, String consumerName, Runnable handler) {
    Objects.requireNonNull(event, "event must not be null");
    Objects.requireNonNull(handler, "handler must not be null");
    if (consumerName == null || consumerName.isBlank()) {
      throw new IllegalArgumentException("consumerName must not be null or blank");
    }
    if (!eventStore.tryMarkProcessed(event.eventId(), consumerName)) {
      return false;
    }
    handler.run();
    return true;
  }
}
