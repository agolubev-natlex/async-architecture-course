package ru.natlex.authservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.natlex.authservice.entity.UserEntity;
import ru.natlex.authservice.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createNewUser(@RequestBody UserCreateRequestDto user) {
        UserEntity entity = new UserEntity();
        entity.setRole(UserEntity.Role.valueOf(user.role()));
        entity.setUsername(user.username());
        entity.setPasswordHash(user.password());
        userService.createUser(entity);
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getAll();
    }

    record UserCreateRequestDto(String username, String password, String role) {

    }
}
