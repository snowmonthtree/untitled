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


}
