package ru.natlex.taskservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.natlex.taskservice.entity.UserPublicAccount;
import ru.natlex.taskservice.repository.UserPublicAccountRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class UserPublicAccountService {
    private final UserPublicAccountRepository userRepository;

    public void createUserPublicAccount(UserPublicAccount userPublicAccount) {
        if (userRepository.existsByPublicId(userPublicAccount.getPublicId())) {
            return;
        }
        userRepository.save(userPublicAccount);
    }

    public List<UserPublicAccount> getAll() {
        return userRepository.findAll();
    }
}
