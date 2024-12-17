package org.example.controller;

import org.example.enity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/init")
    public List<Audit> init(){
        return auditRepository.OrderByAuditTimeDesc();
    }
    @GetMapping("/{auditId}")
    public Audit getAudit(@PathVariable String auditId){
        return auditRepository.findByAuditId(auditId);
    }
    @PostMapping("/upload")
    public String uploadAudit(@RequestParam String userId,@RequestParam String resourceId,@RequestParam String auditName) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "User not found";
        }
        LedResource resource = ledResourceRepository.findByResourceId(resourceId);
        if (resource == null) {
            return "Resource not found";
        }
        Audit audit = new Audit();
        audit.setUser(user);
        audit.setResource(resource);
        audit.setAuditName(auditName);
        audit.setAuditUrl(resource.getResourceWebUrl());
        audit.setAuditTime(new Timestamp(System.currentTimeMillis()));
        auditRepository.save(audit);
        return "Audit uploaded successfully";
    }
    @PostMapping("/delete-audit")
    public String deleteAudit(@RequestParam String resourceId) {
        List<Audit> audits = auditRepository.findByResource_ResourceId(resourceId);
        for (Audit audit1:audits){
            auditRepository.delete(audit1);
        }
        return "Audit deleted successfully";
    }

}
