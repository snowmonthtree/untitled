package org.example.service;

import org.example.enity.Feedback;

import java.util.List;

public interface FeedBackService {
    public String addComment(String userId, String context);
    public List<Feedback> getAllFeedback();
}
