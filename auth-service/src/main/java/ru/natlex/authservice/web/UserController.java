package ru.natlex.authservice.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.natlex.authservice.messaging.KafkaProducer;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final KafkaProducer kafkaProducer;

    @GetMapping
    public void createUser() {
        kafkaProducer.sendMessage();
    }
}
