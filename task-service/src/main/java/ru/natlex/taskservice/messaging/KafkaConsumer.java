package ru.natlex.taskservice.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.natlex.taskservice.entity.UserPublicAccount;
import ru.natlex.taskservice.service.UserPublicAccountService;

@AllArgsConstructor
@Component
@Slf4j
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final UserPublicAccountService userPublicAccountService;

    @KafkaListener(id = "group-1", topics = "${kafka.topics.users-topic}")
    public void consume(@Payload String message) throws JsonProcessingException {
        log.info("Message consumed. Creating user...");
        UserPublicId userPublicAccount = objectMapper.readValue(message, UserPublicId.class);
        UserPublicAccount account = new UserPublicAccount();
        account.setPublicId(userPublicAccount.publicId());
        userPublicAccountService.createUserPublicAccount(account);
        log.info("User created");
    }

    record UserPublicId(String publicId) {
    }

}
