package org.example.service;

import org.example.entity.Feedback;

import java.util.List;

public interface FeedBackService {
    String addFeedback(String userId, String context);
    List<Feedback> getAllFeedback();
    String deleteFeedback(String feedbackId);
}
