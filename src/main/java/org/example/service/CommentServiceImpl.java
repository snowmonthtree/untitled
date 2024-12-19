package org.example.service;

import org.example.entity.Comment;
import org.example.entity.LedResource;
import org.example.entity.User;
import org.example.repository.CommentRepository;
import org.example.repository.LedResourceRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String addComment(String resourceId, String userId, String commentContext) {
        Optional<LedResource> optionalLedResource = ledResourceRepository.findById(resourceId);
        if (optionalLedResource.isEmpty()) {
            return "LedResource not found";
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        LedResource ledResource = optionalLedResource.get();
        User user = optionalUser.get();

        Comment comment = new Comment();
        comment.setLedResource(ledResource);
        comment.setUser(user);
        comment.setCommentContext(commentContext);
        comment.setCommentTime(new Timestamp(System.currentTimeMillis()));

        commentRepository.save(comment);

        return "Comment added successfully";
    }

    @Override
    public String deleteComment(String commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            return "Comment not found";
        }
        commentRepository.delete(optionalComment.get());
        return "Comment deleted successfully";
    }
}
