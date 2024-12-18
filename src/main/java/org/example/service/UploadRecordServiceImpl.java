package org.example.service;

import org.example.enity.UploadRecord;
import org.example.enity.User;
import org.example.repository.LedResourceRepository;
import org.example.repository.UploadRecordRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadRecordServiceImpl implements UploadRecordService{
    @Autowired
    private UploadRecordRepository uploadRecordRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UploadRecord> showUploadRecord(String userId) {
        User user = userRepository.findByUserId(userId);
        List<UploadRecord> uploadRecords = uploadRecordRepository.findByUser_UserId(userId);
        return uploadRecords;
    }
}
