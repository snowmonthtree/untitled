package org.example.service;

import org.example.enity.Feedback;

import java.util.List;

public interface FeedBackService {
    String addComment(String userId, String context);
    List<Feedback> getAllFeedback();
}
