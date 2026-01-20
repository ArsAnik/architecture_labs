package com.booking.publisher;

import com.booking.config.JmsConfig;
import com.booking.entity.AuditChangeDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuditMessagePublisher {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void publishAudit(String entityType, Integer entityId, String action, 
                           Map<String, Object> oldValues, Map<String, Object> newValues) {
        AuditChangeDB audit = new AuditChangeDB(entityType, entityId, action, oldValues, newValues);
        jmsTemplate.convertAndSend(JmsConfig.AUDIT_TOPIC, audit);
    }
}