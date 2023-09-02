package ru.natlex.accountingservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.natlex.accountingservice.entity.AuditLog;
import ru.natlex.accountingservice.service.AuditLogService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/logs/")
@RestController
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ACCOUNTANT', 'MANAGER')")
    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditLogService.getAll();
    }

    @PreAuthorize("hasAuthority('POPUG')")
    @GetMapping("my")
    public List<AuditLog> getMyLogs() {
        String userPublicId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return auditLogService.getLogsByUserPublicId(userPublicId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'ACCOUNTANT', 'MANAGER')")
    @GetMapping("by-user/{userPublicId}")
    public List<AuditLog> getPopugLogs(@PathVariable String userPublicId) {
        return auditLogService.getLogsByUserPublicId(userPublicId);
    }
}
