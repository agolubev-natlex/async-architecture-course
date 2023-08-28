package ru.natlex.taskservice;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import ru.natlex.commons.config.utils.KafkaTopics;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        return Map.ofEntries(
                Map.entry(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092"),
                Map.entry(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class),
                Map.entry(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
        );
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaAdmin.NewTopics topics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(KafkaTopics.TASK_SERVICE_TASK_CREATED_TOPIC)
                        .replicas(1)
                        .partitions(1)
                        .build(),
                TopicBuilder.name(KafkaTopics.TASK_SERVICE_TASK_UPDATED_TOPIC)
                        .replicas(1)
                        .partitions(1)
                        .build(),
                TopicBuilder.name(KafkaTopics.TASK_SERVICE_TASK_CLOSED_TOPIC)
                        .replicas(1)
                        .partitions(1)
                        .build(),
                TopicBuilder.name(KafkaTopics.TASK_SERVICE_TASK_RESHUFFLED_TOPIC)
                        .replicas(1)
                        .partitions(1)
                        .build()
        );
    }
}
