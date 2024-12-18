package org.example.service;

import org.example.enity.UploadRecord;

import java.util.List;

public interface UploadRecordService {
    List<UploadRecord> showUploadRecord(String userId);
}
