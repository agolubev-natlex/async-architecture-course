package ru.natlex.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.natlex.taskservice.entity.UserPublicAccount;

public interface UserPublicAccountRepository extends JpaRepository<UserPublicAccount, Long> {
    boolean existsByPublicId(String userPublicId);
}
