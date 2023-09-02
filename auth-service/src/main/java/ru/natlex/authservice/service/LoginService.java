package ru.natlex.authservice.service;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.natlex.commons.config.JwtTokenProvider;

@AllArgsConstructor
@Service
public class LoginService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(String username, String password) {
        var user = userService.getByUserName(username);
        if (passwordEncoder.matches(password, user.getPasswordHash())) {
            return jwtTokenProvider.createToken(user.getPublicId(), user.getRole().name());
        }
        throw new AccessDeniedException("Пользователь или пароль неверны");
    }
}
