package org.example.controller;

import org.example.enity.*;
import org.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/find/resource/{resourceId}")
    public ResponseEntity<List<Comment>> getCommentsByResourceId(@PathVariable String resourceId) {
        List<Comment> comments = commentService.getCommentsByResourceId(resourceId);
        if (comments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/add/resource/{resourceId}/{userId}")
    public ResponseEntity<String> addComment(@PathVariable String resourceId,
                                             @PathVariable String userId,
                                             @RequestBody String commentContext) {
        return commentService.addComment(resourceId, userId, commentContext);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String commentId) {
        return commentService.deleteComment(commentId);
    }
}
