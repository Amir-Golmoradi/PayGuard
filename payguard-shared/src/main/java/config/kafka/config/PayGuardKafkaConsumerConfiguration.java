package config.kafka.config;

import exception.InvalidKafkaEventException;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Opt-in error handling for non-transactional Kafka listener containers.
 * Consumers must configure their own group ID and create topic.DLT with at
 * least as many partitions as the source topic before consuming.
 * Do not use this handler with a transactional Kafka listener container.
 */
@Configuration(proxyBeanMethods = false)
public class PayGuardKafkaConsumerConfiguration {

   @Bean
   public DefaultErrorHandler payGuardKafkaErrorHandler(
           KafkaTemplate<String, Object> kafkaTemplate) {
      var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
              (record, error) -> new TopicPartition(record.topic() + ".DLT",
                      record.partition()));
      // Two retries after the first failure: three total processing attempts.
      var handler = new DefaultErrorHandler(recoverer, new FixedBackOff(1_000L, 2L));
      handler.addNotRetryableExceptions(InvalidKafkaEventException.class);
      return handler;
   }
}
