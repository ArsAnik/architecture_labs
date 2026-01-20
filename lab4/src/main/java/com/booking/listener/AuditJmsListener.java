package com.booking.listener;

import com.booking.config.JmsConfig;
import com.booking.entity.AuditChangeDB;
import com.booking.repository.AuditChangeDBRepository;
import com.booking.validator.AuditMessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.lang.System;

@Component
public class AuditJmsListener {

    @Autowired private AuditChangeDBRepository auditRepo;
    @Autowired private AuditMessageValidator validator;

    @JmsListener(
        destination = JmsConfig.AUDIT_TOPIC,
        subscription = "audit-sub"
    )
    public void onMessage(AuditChangeDB audit) {
        if (!validator.isValid(audit)) return;
        System.out.println("Audit saved: " + audit.getEntityType() + " " + audit.getAction());
        auditRepo.save(audit);
    }
}
