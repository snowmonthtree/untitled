package org.example.controller;

import org.example.enity.LedResource;
import org.example.enity.Feedback;
import org.example.enity.User;
import org.example.repository.FeedbackRepository;
import org.example.repository.LedResourceRepository;
import org.example.repository.LikesRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/add")
    public ResponseEntity<String> addComment(@PathVariable String userId,
                                             @RequestBody String context) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setFeedbackContent(context);
        feedback.setFeedbackDate(new Timestamp(System.currentTimeMillis()));

        feedbackRepository.save(feedback);

        return ResponseEntity.ok("Feedback added successfully");
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedback=feedbackRepository.findByOrderByFeedbackDateDesc();
        return ResponseEntity.ok(feedback);
    }
}
