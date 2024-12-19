package org.example.service;

import org.example.entity.Feedback;

import java.util.List;

public interface FeedBackService {
    String addComment(String userId, String context);
    List<Feedback> getAllFeedback();
}
