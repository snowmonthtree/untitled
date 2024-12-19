package org.example.repository;

import org.example.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, String>{
    @Query("SELECT fb FROM Feedback fb ORDER BY fb.feedbackDate DESC")
    List<Feedback> findByOrderByFeedbackDateDesc();
    @Query("SELECT fb FROM Feedback fb ORDER BY fb.feedbackDate ASC")
    List<Feedback> findByOrderByFeedbackDateAsc();
}
