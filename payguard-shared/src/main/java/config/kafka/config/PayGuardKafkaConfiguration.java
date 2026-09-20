package config.kafka.config;

import config.kafka.publisher.PayGuardKafkaEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/** Opt-in shared publisher configuration for services that publish Kafka events. */
@Configuration(proxyBeanMethods = false)
public class PayGuardKafkaConfiguration {

  @Bean
  public PayGuardKafkaEventPublisher payGuardKafkaEventPublisher(
      KafkaTemplate<String, Object> kafkaTemplate) {
    return new PayGuardKafkaEventPublisher(kafkaTemplate);
  }
}
