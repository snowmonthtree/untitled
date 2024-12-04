package org.example.controller;

import org.example.enity.Comment;
import org.example.enity.User;
import org.example.enity.LedResource;
import org.example.repository.LedResourceRepository;
import org.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @GetMapping("/find/resource/{resourceId}")
    public ResponseEntity<List<Comment>> getCommentsByResourceId(@PathVariable String resourceId) {
        List<Comment> comments = commentRepository.findByLedResource_ResourceId(resourceId);
        if (comments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }


    @PostMapping("/add/resource/{resourceId}/{userId}")
    public ResponseEntity<String> addComment(@PathVariable String resourceId,
                                             @PathVariable String userId,
                                             @RequestBody String commentContext) {
        // 假设 LedResource 和 User 的获取逻辑已经实现
        LedResource ledResource = new LedResource();
        ledResource.setResourceId(resourceId);

        User user = new User();
        user.setUserId(userId);

        Comment comment = new Comment();
        comment.setLedResource(ledResource);
        comment.setUser(user);
        comment.setCommentContext(commentContext);
        comment.setCommentTime(new Timestamp(System.currentTimeMillis()));

        commentRepository.save(comment);

        return ResponseEntity.ok("Comment added successfully");
    }
}

