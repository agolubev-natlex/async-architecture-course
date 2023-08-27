package ru.natlex.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.natlex.authservice.service.LoginService;

@RequiredArgsConstructor
@RestController
@RequestMapping("login/")
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public AccessTokenResponse login(@RequestBody LoginBody loginBody) {
        String accessToken = loginService.login(loginBody.username(), loginBody.password());
        return new AccessTokenResponse(accessToken);
    }

    record AccessTokenResponse(String accessToken) {
    }

    record LoginBody(String username, String password) {

    }

}
