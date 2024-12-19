package org.example.service;

import org.example.entity.PlayRecord;

import java.util.List;

public interface PlayRecordService {
    List<PlayRecord> showPlayRecordByUserId(String userId);
    String addPlayRecord(String userId, String resourceId);
}
