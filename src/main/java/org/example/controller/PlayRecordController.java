package org.example.controller;

import org.example.enity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/play-records")
public class PlayRecordController {

    @Autowired
    private PlayRecordRepository playRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LedResourceRepository ledResourceRepository;
    @GetMapping("/show/{userId}")
    public ResponseEntity<List<PlayRecord>> showPlayRecordByUserId(@PathVariable String userId) {
        List<PlayRecord> playRecord = playRecordRepository.findByUser_UserIdOrderByPlayTimeDesc(userId);
        if (playRecord.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playRecord);
    }

    @PostMapping("/add/{userId}/{resourceId}")
    public ResponseEntity<Void> addPlayRecord(@PathVariable String userId, @PathVariable String resourceId) {
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
        } else {
            // 如果存在，则更新播放时间
            PlayRecordId id = playRecord.getId();
            id.setPlayTime(new Timestamp(System.currentTimeMillis()));
            playRecord.setId(id);
        }

        playRecordRepository.save(playRecord);
        return ResponseEntity.ok().build();
    }




}


