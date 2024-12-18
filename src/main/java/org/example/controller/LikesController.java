package org.example.controller;

import org.example.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikesController {
    @Autowired
    private LikesService likesService;

    @GetMapping("/userIfLike")
    public ResponseEntity<Boolean> userIfLike(@RequestParam String userId, @RequestParam String resourceId) {
        return ResponseEntity.ok(likesService.userIfLike(userId, resourceId));
    }

    @PostMapping("/userLike")
    public ResponseEntity<String> likeResource(@RequestParam String userId, @RequestParam String resourceId) {
        return ResponseEntity.ok(likesService.likeResource(userId, resourceId));
    }
    //获取点赞量
    @GetMapping("/likesnum")
    public ResponseEntity<Integer> getLikesNum(@RequestParam String resourceId) {
        return ResponseEntity.ok(likesService.getLikesNum(resourceId));
    }

}
