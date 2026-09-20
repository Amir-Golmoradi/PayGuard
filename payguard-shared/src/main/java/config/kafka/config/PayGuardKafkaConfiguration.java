package config.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Opt-in shared publisher configuration. Import this configuration only in
 * services that publish Kafka events. Spring Boot provides KafkaTemplate.
 */
@Configuration(proxyBeanMethods = false)
public class PayGuardKafkaConfiguration {

   @Bean
   public PayGuardKafkaEventPublisher payGuardKafkaEventPublisher(
           KafkaTemplate<String, Object> kafkaTemplate) {
      return new PayGuardKafkaEventPublisher(kafkaTemplate);
   }
}
