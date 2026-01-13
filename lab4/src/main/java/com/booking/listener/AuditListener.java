package com.booking.listener;

import com.booking.entity.AuditChangeDB;
import com.booking.event.AuditEvent;
import com.booking.repository.AuditChangeDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {

    private final AuditChangeDBRepository auditRepo;

    @Autowired
    public AuditListener(AuditChangeDBRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    @EventListener
    public void handleAuditEvent(AuditEvent event) {
        AuditChangeDB audit = new AuditChangeDB(
                event.getEntityType(),
                event.getEntityId(),
                event.getChangeType(),
                event.getOldValues(),
                event.getNewValues()
        );
        auditRepo.save(audit);
    }
}