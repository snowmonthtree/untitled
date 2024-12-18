package org.example.service;

import org.example.enity.Feedback;
import org.example.enity.User;
import org.example.repository.FeedbackRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class FeedBackServiceImpl implements FeedBackService{
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public String addComment(String userId,String context) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return "User not found";
        }

        User user = optionalUser.get();

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setFeedbackContent(context);
        feedback.setFeedbackDate(new Timestamp(System.currentTimeMillis()));

        feedbackRepository.save(feedback);

        return "Feedback added successfully";
    }
    @Override
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbacks=feedbackRepository.findByOrderByFeedbackDateDesc();
        return feedbacks;
    }
}
