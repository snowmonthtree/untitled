package org.example.service;

import org.example.entity.UploadRecord;

import java.util.List;

public interface UploadRecordService {
    List<UploadRecord> showUploadRecord(String userId);
}
