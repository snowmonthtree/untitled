package org.example.service;

import org.example.enity.*;
import org.example.repository.*;
import org.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LedResourceRepository ledResourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Comment> getCommentsByResourceId(String resourceId) {
        return commentRepository.findByLedResource_ResourceId(resourceId);
    }

    @Override
    public ResponseEntity<String> addComment(String resourceId, String userId, String commentContext) {
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

    @Override
    public ResponseEntity<String> deleteComment(String commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (!optionalComment.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        commentRepository.delete(optionalComment.get());
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
