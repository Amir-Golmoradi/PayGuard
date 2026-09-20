package config.kafka.publisher;

import config.kafka.event.EventPayload;
import config.kafka.event.PayGuardEvent;
import exception.InvalidKafkaEventException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

/** Publishes validated events using their aggregate ID as the Kafka key. */
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
