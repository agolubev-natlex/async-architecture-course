package ru.natlex.taskservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class KafkaProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    public void publishMessage(String topic, Object event) {
        kafkaTemplate.send(topic, objectMapper.writeValueAsString(event));
    }
}
