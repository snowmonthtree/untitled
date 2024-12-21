package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringFeedbackIdGenerator")
    @Column(name = "Feedback_ID", nullable = false, length = 11)
    private String feedbackId;

    @ManyToOne
    @JoinColumn(name = "User_ID", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "Feedback_Content", nullable = false, length = 2024)
    private String feedbackContent;

    @Column(name = "Feedback_Date", nullable = false)
    private Timestamp feedbackDate;

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public User getUser() {
        return user;
    }
    public String getUserId() {
        return user != null ? user.getName() : null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public Timestamp getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Timestamp feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

}