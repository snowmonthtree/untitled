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
        Optional<Likes> optionalLikes = likesRepository.findByUser_UserIdAndLedResource_ResourceId(userId, resourceId);
        return ResponseEntity.ok(optionalLikes.isPresent());
    }


    @PostMapping("/userLike")
    public ResponseEntity<String> likeResource(@RequestParam String userId, @RequestParam String resourceId) {
        try {
            Optional<Likes> optionalLikes = likesRepository.findByUser_UserIdAndLedResource_ResourceId(userId, resourceId);
            if (optionalLikes.isPresent()) {
                likesRepository.delete(optionalLikes.get());
                return ResponseEntity.ok("取消点赞");
            } else {
                Likes likes = new Likes();
                likesRepository.save(likes);
                return ResponseEntity.ok("点赞成功");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error liking resource: " + e.getMessage());
        }
    }
//    @GetMapping("/getLikeNum")
//    public ResponseEntity<Integer> getLikeNum(@RequestParam String resourceId) {
//        Optional<LedResource> optionalLedResource = ledResourceRepository.findById(resourceId);
//        if (optionalLedResource.isPresent()) {
//            LedResource ledResource = optionalLedResource.get();
//            return ResponseEntity.ok(ledResource.getLikes());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
