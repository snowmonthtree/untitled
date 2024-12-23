package org.example.controller;

import org.example.entity.UploadRecord;
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
    private UploadRecordService uploadRecordService;
    @PostMapping("/show")
    public ResponseEntity<List<UploadRecord>> showUploadRecord(@RequestParam String userId) {
        List<UploadRecord> uploadRecordList = uploadRecordService.showUploadRecord(userId);
        return new ResponseEntity<>(uploadRecordList, HttpStatus.OK);
    }

}
