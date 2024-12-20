package org.example.repository;

import org.example.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    // 根据 resourceId 查找所有评论
    List<Comment> findByLedResource_ResourceId(String resourceId);
    Comment findByCommentId(String commentId);
    List<Comment> findAllByOrderByCommentTimeDesc();
    List<Comment> findByUser_UserIdOrderByCommentTimeDesc(String userId);
}