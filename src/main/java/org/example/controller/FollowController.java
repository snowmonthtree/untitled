package org.example.controller;

import org.example.entity.Follow;
import org.example.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    @Autowired
    private FollowService followService;
    @PostMapping("/user-follow")
    public String addFollow(@RequestParam String followerId, @RequestParam String followingId) {
        return followService.addFollow(followerId, followingId);
    }
    @PostMapping("user-if-follow")
    public Boolean userIfFollow(@RequestParam String followerId, @RequestParam String followingId) {
        return followService.userIfFollow(followerId, followingId);
    }
    @GetMapping("/get-followers/{userId}")
    public ResponseEntity<List<Follow>> getUserFollowers(@PathVariable String userId) {
        List<Follow> followers = followService.getUserFollowers(userId);
        return ResponseEntity.ok(followers);
    }
    @GetMapping("/get-followings/{userId}")
    public ResponseEntity<List<Follow>> getUserFollowings(@PathVariable String userId) {
        List<Follow> followings = followService.getUserFollowings(userId);
        return ResponseEntity.ok(followings);
    }
}
