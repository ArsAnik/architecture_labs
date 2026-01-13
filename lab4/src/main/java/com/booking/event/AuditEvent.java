package com.booking.event;

import java.util.Map; 
import org.springframework.context.ApplicationEvent;

public class AuditEvent extends ApplicationEvent {
    private final String entityType;
    private final Integer entityId;
    private final String changeType;
    private final Map<String, Object> oldValues;
    private final Map<String, Object> newValues;

    public AuditEvent(Object source, String entityType, Integer entityId, String changeType, Map<String, Object> oldValues, Map<String, Object> newValues) {
        super(source);
        this.entityType = entityType;
        this.entityId = entityId;
        this.changeType = changeType;
        this.oldValues = oldValues;
        this.newValues = newValues;
    }

    public String getEntityType() { return entityType; }
    public Integer getEntityId() { return entityId; }
    public String getChangeType() { return changeType; }
    public Map<String, Object> getOldValues() { return oldValues; }
    public Map<String, Object> getNewValues() { return newValues; }
}
