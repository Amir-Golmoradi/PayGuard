package config.kafka.consumer;

import config.kafka.event.EventPayload;
import config.kafka.event.PayGuardEvent;

import java.util.Objects;

/**
 * Skips duplicate deliveries. Caller must invoke process inside its service's
 * local database transaction. A thrown handler exception must roll back the
 * marker as well as the business change; do not swallow that exception.
 */
public final class IdempotentEventProcessor {
   private final ProcessedEventStore eventStore;

   public IdempotentEventProcessor(ProcessedEventStore eventStore) {
      this.eventStore = Objects.requireNonNull(eventStore, "eventStore must not be null");
   }

   /**
    * @return true when handler ran, false when this consumer handled the event before
    */
   public boolean process(PayGuardEvent<? extends EventPayload> event, String consumerName, Runnable handler) {
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

