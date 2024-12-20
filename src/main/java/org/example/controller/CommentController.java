package org.example.controller;

import org.example.entity.Comment;
import org.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            String response = commentService.addComment(resourceId, userId, commentContext);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add comment");
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String commentId) {
        try {
            String response = commentService.deleteComment(commentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete comment");
        }
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/get-user-comment")
    public ResponseEntity<List<Comment>> getUserComments(@RequestParam String userId) {
        return ResponseEntity.ok(commentService.getUserComments(userId));
    }
}
