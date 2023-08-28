package ru.natlex.authservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka.topics.users-topic}")
    private String topic;

    @SneakyThrows
    public void sendUserMessage(Object body) {
        kafkaTemplate.send(topic, objectMapper.writeValueAsString(body));
    }
}
