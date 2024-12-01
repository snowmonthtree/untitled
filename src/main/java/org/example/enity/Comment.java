package org.example.enity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @Column(name = "Cmment_ID", length = 11)
    private String commentId;

    @ManyToOne
    @JoinColumn(name = "Resource_ID", referencedColumnName = "Resource_ID", nullable = false)
    private LedResource ledResource;  // Assuming LedResource is the resource being commented on

    @ManyToOne
    @JoinColumn(name = "User_ID", referencedColumnName = "User_ID", nullable = false)
    private User user;  // Assuming User entity exists

    @Column(name = "Comment_Context", nullable = false, length = 255)
    private String commentContext;

    @Column(name = "Comment_Time", nullable = false)
    private Timestamp commentTime;

    // Getters and Setters
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public LedResource getLedResource() {
        return ledResource;
    }

    public void setLedResource(LedResource ledResource) {
        this.ledResource = ledResource;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentContext() {
        return commentContext;
    }

    public void setCommentContext(String commentContext) {
        this.commentContext = commentContext;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }
}

