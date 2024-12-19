package org.example.service;
import org.example.entity.Audit;

import java.util.List;

public interface AuditService {
    List<Audit> OrderByAuditTimeDesc();
    String uploadAudit(String userId, String resourceId, String auditName);
    String deleteAudit(String resourceId);
    Audit findByAuditId(String auditId);
}
