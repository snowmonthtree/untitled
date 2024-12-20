package org.example.service;

import org.example.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByResourceId(String resourceId);
    String addComment(String resourceId, String userId, String commentContext);
    String deleteComment(String commentId);
    List<Comment> getAllComments();
    List<Comment> getUserComments(String userId);
}
