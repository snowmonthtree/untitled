package org.example.repository;

import org.example.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, String> {
    List<Audit> OrderByAuditTimeDesc();

    Audit findByAuditId(String auditId);

    Audit findByResource_ResourceId(String resourceId);
}