package ru.natlex.taskservice.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.natlex.taskservice.entity.UserPublicAccount;
import ru.natlex.taskservice.service.UserPublicAccountService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class UserAccountController {

    private final UserPublicAccountService userPublicAccountService;

    @GetMapping
    public List<UserPublicAccount> getAccounts() {
        return userPublicAccountService.getAll();
    }
}
