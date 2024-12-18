package org.example.service;

import org.example.enity.LedResource;
import org.example.enity.PlayRecord;
import org.example.enity.PlayRecordId;
import org.example.enity.User;
import org.example.repository.LedResourceRepository;
import org.example.repository.PlayRecordRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class PlayRecordServiceImpl implements PlayRecordService{

    @Autowired
    private PlayRecordRepository playRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Override
    public List<PlayRecord> showPlayRecordByUserId(String userId) {
        List<PlayRecord> playRecords = playRecordRepository.findByUser_UserIdOrderByPlayTimeDesc(userId);
        if (playRecords.isEmpty()) {
            return null;
        }
        return playRecords;
    }

    @Override
    public String addPlayRecord(String userId, String resourceId) {
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            return "no user";
        }

        User user = optionalUser.get();
        PlayRecord playRecord = playRecordRepository.findByUser_UserIdAndLedResource_ResourceId(userId, resourceId);

        if (playRecord == null) {
            // 如果不存在，则创建新的PlayRecord
            PlayRecordId id = new PlayRecordId();
            id.setResourceId(resourceId);
            id.setUserId(userId);
            id.setPlayTime(new Timestamp(System.currentTimeMillis()));
            playRecord = new PlayRecord();
            playRecord.setId(id);
            playRecord.setLedResource(ledResource);
            playRecord.setUser(user);
            playRecordRepository.save(playRecord);
        } else {
            // 如果存在，则更新播放时间
            PlayRecordId id = playRecord.getId();
            id.setPlayTime(new Timestamp(System.currentTimeMillis()));
            playRecord.setId(id);
            playRecordRepository.save(playRecord);
        }
        return "playrecord saved";
    }
}
