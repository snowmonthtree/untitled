package org.example.controller;

import org.example.enity.Audit;
import org.example.repository.AuditRepository;
import org.example.repository.LedResourceRepository;
import org.example.repository.UserRepository;
import org.example.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditService auditService;

    @GetMapping("/init")
    public List<Audit> init(){
        return auditService.OrderByAuditTimeDesc();
    }
    @GetMapping("/{auditId}")
    public Audit getAudit(@PathVariable String auditId){
        return auditService.findByAuditId(auditId);
    }
    @PostMapping("/user-audit")
    public String uploadAudit(@RequestParam String userId,@RequestParam String resourceId,@RequestParam String auditName) {
        return auditService.uploadAudit(userId,resourceId,auditName);
    }
    @PostMapping("/delete-audit")
    public String deleteAudit(@RequestParam String resourceId) {
        return auditService.deleteAudit(resourceId);
    }

}
