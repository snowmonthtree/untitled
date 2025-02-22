package org.example.controller;

import org.example.entity.Feedback;
import org.example.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedBackService feedBackService;
    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addFeedback(@PathVariable String userId,
                                             @RequestBody String context) {
        try {
            return ResponseEntity.ok(feedBackService.addFeedback(userId, context));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add comment");
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbacks=feedBackService.getAllFeedback();
        return ResponseEntity.ok(feedbacks);
    }
    @GetMapping("/delete")
    public ResponseEntity<String> deleteFeedback(@RequestParam String feedbackId) {
        try {
            return ResponseEntity.ok(feedBackService.deleteFeedback(feedbackId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete feedback");
        }
    }

}
