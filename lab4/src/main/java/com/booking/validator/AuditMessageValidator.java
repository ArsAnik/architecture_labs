package com.booking.validator;

import com.booking.entity.AuditChangeDB;
import org.springframework.stereotype.Component;

@Component
public class AuditMessageValidator {

    public boolean isValid(AuditChangeDB audit) {
        if (audit == null || audit.getEntityType() == null || 
            audit.getEntityId() == null || audit.getAction() == null) {
            System.err.println("Invalid audit message structure: " + audit);
            return false;
        }
        
        if (!audit.getEntityType().matches("^(client|booking)$")) {
            System.err.println("Unsupported entityType: " + audit.getEntityType());
            return false;
        }
        
        if (!audit.getAction().matches("^(create|update|delete)$")) {
            System.err.println("Invalid action: " + audit.getAction());
            return false;
        }
        
        return true;
    }
}
