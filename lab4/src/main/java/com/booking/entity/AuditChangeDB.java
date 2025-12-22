package com.booking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "audit_changes_db")
public class AuditChangeDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "entity_type", nullable = false)
    private String entityType;
    
    @Column(name = "entity_id", nullable = false)
    private Integer entityId;
    
    @Column(name = "action", nullable = false)
    private String action;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "old_values", columnDefinition = "jsonb")
    private Map<String, Object> oldValues;
    
    @JdbcTypeCode(SqlTypes.JSON) 
    @Column(name = "new_values", columnDefinition = "jsonb")
    private Map<String, Object> newValues;
    
    @Column(name = "changed_at")
    private LocalDateTime changedAt = LocalDateTime.now();

    public static AuditChangeDB create(String entityType, Integer entityId, String action, 
                                     Map<String, Object> oldValues, Map<String, Object> newValues) {
        AuditChangeDB record = new AuditChangeDB();
        record.entityType = entityType.toLowerCase();
        record.entityId = entityId;
        record.action = action.toLowerCase();
        record.oldValues = oldValues != null ? oldValues : Map.of();
        record.newValues = newValues != null ? newValues : Map.of();
        return record;
    }

    public Integer getId() { return id; } public void setId(Integer id) { this.id = id; }
    public String getEntityType() { return entityType; } public void setEntityType(String entityType) { this.entityType = entityType; }
    public Integer getEntityId() { return entityId; } public void setEntityId(Integer entityId) { this.entityId = entityId; }
    public String getAction() { return action; } public void setAction(String action) { this.action = action; }
    public Map<String, Object> getOldValues() { return oldValues; } public void setOldValues(Map<String, Object> oldValues) { this.oldValues = oldValues; }
    public Map<String, Object> getNewValues() { return newValues; } public void setNewValues(Map<String, Object> newValues) { this.newValues = newValues; }
    public LocalDateTime getChangedAt() { return changedAt; } public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
}
