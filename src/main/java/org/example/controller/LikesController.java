package org.example.controller;

import org.example.enity.LedResource;
import org.example.enity.Likes;
import org.example.repository.LedResourceRepository;
import org.example.repository.LikesRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikesController {

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private LedResourceRepository ledResourceRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/userIfLike")
    public ResponseEntity<Boolean> userIfLike(@RequestParam String userId, @RequestParam String resourceId) {
        Optional<Likes> optionalLikes = likesRepository.findByUserIdAndResourceId(userId, resourceId);
        return ResponseEntity.ok(optionalLikes.isPresent());
    }

    @PostMapping("/userLike")
    public ResponseEntity<String> likeResource(@RequestParam String userId, @RequestParam String resourceId) {
        try {
            Optional<Likes> optionalLikes = likesRepository.findByUserIdAndResourceId(userId, resourceId);
            if (optionalLikes.isPresent()) {
                return ResponseEntity.badRequest().body("Resource already liked");
            } else {
                Likes likes = new Likes();
                likesRepository.save(likes);
                return ResponseEntity.ok("Resource liked successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error liking resource: " + e.getMessage());
        }
    }

    @PostMapping("/userUnlike")
    public ResponseEntity<String> unlikeResource(@RequestParam String userId, @RequestParam String resourceId) {
        try {
            Optional<Likes> optionalLikes = likesRepository.findByUserIdAndResourceId(userId, resourceId);
            if (optionalLikes.isPresent()) {
                likesRepository.delete(optionalLikes.get());
                return ResponseEntity.ok("Resource unliked successfully");
            } else {
                return ResponseEntity.badRequest().body("Resource not found or already unliked");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error unliking resource: " + e.getMessage());
        }
    }
}
