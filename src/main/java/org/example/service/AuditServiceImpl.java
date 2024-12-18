package org.example.service;

import org.example.enity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService{
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Override
    public List<Audit> OrderByAuditTimeDesc() {
        return auditRepository.OrderByAuditTimeDesc();
    }

    @Override
    public String uploadAudit(String userId, String resourceId, String auditName) {User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "User not found";
        }
        LedResource resource = ledResourceRepository.findByResourceId(resourceId);
        if (resource == null) {
            return "Resource not found";
        }
        Audit pastAudit = auditRepository.findByResource_ResourceId(resourceId);
        if (pastAudit==null){
            Audit audit = new Audit();
            audit.setUser(user);
            audit.setResource(resource);
            audit.setAuditName(auditName);
            audit.setAuditUrl(resource.getResourceWebUrl());
            audit.setAuditTime(new Timestamp(System.currentTimeMillis()));
            auditRepository.save(audit);}
        return "Audit uploaded successfully";
    }

    @Override
    public String deleteAudit(String resourceId) {
        Audit audit = auditRepository.findByResource_ResourceId(resourceId);
        auditRepository.delete(audit);
        return "Audit deleted successfully";
    }

    @Override
    public Audit findByAuditId(String auditId) {
        return auditRepository.findByAuditId(auditId);
    }
}
