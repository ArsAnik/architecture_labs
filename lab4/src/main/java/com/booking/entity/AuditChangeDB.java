package com.booking.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
    private LocalDateTime changedAt;

    public AuditChangeDB() {}

    @JsonCreator
    public AuditChangeDB(
            @JsonProperty("entityType") String entityType,
            @JsonProperty("entityId") Integer entityId,
            @JsonProperty("action") String action,
            @JsonProperty("oldValues") Map<String, Object> oldValues,
            @JsonProperty("newValues") Map<String, Object> newValues) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.oldValues = oldValues != null ? oldValues : Map.of();
        this.newValues = newValues != null ? newValues : Map.of();
        this.changedAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public Integer getEntityId() { return entityId; }
    public void setEntityId(Integer entityId) { this.entityId = entityId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public Map<String, Object> getOldValues() { return oldValues; }
    public void setOldValues(Map<String, Object> oldValues) { this.oldValues = oldValues; }
    public Map<String, Object> getNewValues() { return newValues; }
    public void setNewValues(Map<String, Object> newValues) { this.newValues = newValues; }
    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
}
