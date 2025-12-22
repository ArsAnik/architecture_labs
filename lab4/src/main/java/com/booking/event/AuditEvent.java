package com.booking.event;

import java.util.Map; 
import org.springframework.context.ApplicationEvent;

public class AuditEvent extends ApplicationEvent {
    private final String entityType;
    private final Integer entityId;
    private final String changeType;
    private final Map<String, Object> values;

    public AuditEvent(Object source, String entityType, Integer entityId, String changeType, Map<String, Object> values) {
        super(source);
        this.entityType = entityType;
        this.entityId = entityId;
        this.changeType = changeType;
        this.values = values;
    }

    public String getEntityType() { return entityType; }
    public Integer getEntityId() { return entityId; }
    public String getChangeType() { return changeType; }
    public Map<String, Object> getValues() { return values; }
}
