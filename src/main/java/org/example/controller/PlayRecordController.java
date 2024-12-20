package org.example.controller;

import org.example.entity.PlayRecord;
import org.example.service.PlayRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/play-records")
public class PlayRecordController {
    @Autowired
    private PlayRecordService playRecordService;
    @GetMapping("/show/{userId}")
    public ResponseEntity<List<PlayRecord>> showPlayRecordByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(playRecordService.showPlayRecordByUserId(userId));
    }

    @PostMapping("/add/{userId}/{resourceId}")
    public ResponseEntity<String> addPlayRecord(@PathVariable String userId, @PathVariable String resourceId) {
        return ResponseEntity.ok(playRecordService.addPlayRecord(userId, resourceId));
    }
}


