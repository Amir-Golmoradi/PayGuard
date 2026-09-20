package config.kafka.config;

import exception.InvalidKafkaEventException;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

/** Opt-in retry and dead-letter configuration for non-transactional Kafka consumers. */
@Configuration(proxyBeanMethods = false)
public class PayGuardKafkaConsumerConfiguration {

  @Bean
  public DefaultErrorHandler payGuardKafkaErrorHandler(
      KafkaTemplate<String, Object> kafkaTemplate) {
    var recoverer =
        new DeadLetterPublishingRecoverer(
            kafkaTemplate,
            (record, error) -> new TopicPartition(record.topic() + ".DLT", record.partition()));
    var handler = new DefaultErrorHandler(recoverer, new FixedBackOff(1_000L, 2L));
    handler.addNotRetryableExceptions(InvalidKafkaEventException.class);
    return handler;
  }
}
