package ru.natlex.accountingservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.natlex.accountingservice.entity.AuditLog;
import ru.natlex.accountingservice.repository.AuditLogRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class AuditLogService {
    private final AuditLogRepository logRepository;

    public void create(AuditLog auditLog) {
        logRepository.save(auditLog);
    }

    @Transactional(readOnly = true)
    public List<AuditLog> getAll() {
        return logRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<AuditLog> getLogsByUserPublicId(String userPublicId) {
        return logRepository.findAllByUserPublicId(userPublicId).toList();
    }
}
