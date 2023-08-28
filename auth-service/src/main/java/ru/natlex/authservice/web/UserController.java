package ru.natlex.authservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.natlex.authservice.entity.UserEntity;
import ru.natlex.authservice.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void createNewUser(@RequestBody UserEntity user) {
        user.setId(null);
        userService.createUser(user);
    }

    @GetMapping
    public List<UserEntity> getUsers() {
        return userService.getAll();
    }
}
