package org.example.controller;

import org.example.enity.*;
import org.example.repository.LedResourceRepository;
import org.example.repository.PlayRecordRepository;
import org.example.repository.UserRepository;
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
        Optional<LedResource> optionalLedResource = ledResourceRepository.findById(resourceId);
        if (!optionalLedResource.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        LedResource ledResource = optionalLedResource.get();
        User user = optionalUser.get();

        PlayRecord playRecord= new PlayRecord();
        playRecord.setResource(ledResource);
        playRecord.setUser(user);
        playRecord.getId().setPlayTime(new Timestamp(System.currentTimeMillis()));

        playRecordRepository.save(playRecord);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/playback-volume")
    public ResponseEntity<Integer> getPlaybackVolume(@RequestParam String resourceId) {
        long count = playRecordRepository.countByResourceId(resourceId);
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        if (ledResource != null) {
            ledResource.setPlaybackVolume((int) count);
            ledResourceRepository.save(ledResource); // 保存更新后的实体
        }
        return ResponseEntity.ok((int) count);
    }
}


