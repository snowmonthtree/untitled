package org.example.service;

import org.example.enity.Comment;
import org.example.enity.LedResource;
import org.example.enity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByResourceId(String resourceId);
    ResponseEntity<String> addComment(String resourceId, String userId, String commentContext);
    ResponseEntity<String> deleteComment(String commentId);
}
