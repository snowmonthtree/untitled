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
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LedResourceRepository ledResourceRepository;

    @Autowired
    private UserRepository userRepository;


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
        Optional<LedResource> optionalLedResource = ledResourceRepository.findById(resourceId);
        if (!optionalLedResource.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LedResource not found");
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        LedResource ledResource = optionalLedResource.get();
        User user = optionalUser.get();

        Comment comment = new Comment();
        comment.setLedResource(ledResource);
        comment.setUser(user);
        comment.setCommentContext(commentContext);
        comment.setCommentTime(new Timestamp(System.currentTimeMillis()));

        commentRepository.save(comment);

        return ResponseEntity.ok("Comment added successfully");
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        commentRepository.delete(optionalComment.get());
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
