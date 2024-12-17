package org.example.enity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

@Entity
@Table(name = "audit")
public class Audit {
    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringAuditIdGenerator")
    @Column(name = "Audit_ID", nullable = false, length = 10)
    private String auditId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Resource_ID")
    private LedResource resource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @Column(name = "Audit_Name", nullable = false, length = 10)
    private String auditName;

    @Column(name = "Audit_Time")
    private Instant auditTime;

    @Column(name = "Audit_URL", nullable = false, length = 11)
    private String auditUrl;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public LedResource getResource() {
        return resource;
    }

    public void setResource(LedResource resource) {
        this.resource = resource;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }

    public Instant getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Instant auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUrl() {
        return auditUrl;
    }

    public void setAuditUrl(String auditUrl) {
        this.auditUrl = auditUrl;
    }

}