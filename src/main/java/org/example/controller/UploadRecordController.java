package org.example.controller;

import org.example.enity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/uploadrecord")
public class UploadRecordController {
    @Autowired
    private UploadRecordRepository uploadRecordRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/show")
    public ResponseEntity<List<UploadRecord>> showUploadRecord(@RequestParam String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<UploadRecord> uploadRecords = uploadRecordRepository.findByUser_UserId(userId);
        return ResponseEntity.ok(uploadRecords);
    }

}
