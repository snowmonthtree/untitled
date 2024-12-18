package org.example.controller;

import org.example.enity.UploadRecord;
import org.example.repository.LedResourceRepository;
import org.example.repository.UploadRecordRepository;
import org.example.repository.UserRepository;
import org.example.service.UploadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/uploadrecord")
public class UploadRecordController {
    @Autowired
    private UploadRecordRepository uploadRecordRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UploadRecordService uploadRecordService;
    @PostMapping("/show")
    public ResponseEntity<List<UploadRecord>> showUploadRecord(@RequestParam String userId) {
        List<UploadRecord> uploadRecordList = uploadRecordService.showUploadRecord(userId);
        return new ResponseEntity<>(uploadRecordList, HttpStatus.OK);
    }

}
