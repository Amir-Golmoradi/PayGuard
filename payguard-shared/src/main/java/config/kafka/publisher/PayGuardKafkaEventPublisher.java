package config.kafka.publisher;

import config.kafka.event.EventPayload;
import config.kafka.event.PayGuardEvent;
import exception.InvalidKafkaEventException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

/**
 * Publishes a validated event using its aggregate ID as the Kafka record key.
 * The returned future must be observed by the caller (or by an outbox relay).
 * Calling this method is not a database/Kafka atomic transaction.
 */

public final class PayGuardKafkaEventPublisher {

   private final KafkaTemplate<String, Object> kafkaTemplate;

   public PayGuardKafkaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
      this.kafkaTemplate = Objects.requireNonNull(kafkaTemplate, "kafkaTemplate must not be null");
   }

   public CompletableFuture<SendResult<String, Object>> publish(
           String topic, PayGuardEvent<? extends EventPayload> event) {
      if (topic == null || topic.isBlank()) {
         throw new InvalidKafkaEventException("topic must not be blank");
      }
      Objects.requireNonNull(event, "event must not be null");
      return kafkaTemplate.send(topic, event.aggregateId(), event);
   }
}