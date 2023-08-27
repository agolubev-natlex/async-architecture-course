package ru.natlex.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.natlex.authservice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByPublicId(String publicId);
}
