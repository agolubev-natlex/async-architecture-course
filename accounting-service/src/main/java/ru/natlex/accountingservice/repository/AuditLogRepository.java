package ru.natlex.accountingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.natlex.accountingservice.entity.AuditLog;

import java.util.stream.Stream;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    Stream<AuditLog> findAllByUserPublicId(String userPublicId);
}
