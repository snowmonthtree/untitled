package org.example.enity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringCommentIdGenerator")
    @Column(name = "Cmment_ID", length = 11)
    private String commentId;

    @ManyToOne
    @JoinColumn(name = "Resource_ID", referencedColumnName = "Resource_ID", nullable = false)
    @JsonManagedReference
    private LedResource ledResource;  // Assuming LedResource is the resource being commented on

    @ManyToOne
    @JoinColumn(name = "User_ID", referencedColumnName = "User_ID", nullable = false)
    @JsonManagedReference
    private User user;  // Assuming User entity exists

    @Column(name = "Comment_Context", nullable = false, length = 255)
    private String commentContext;

    @Column(name = "Comment_Time", nullable = false)
    private Timestamp commentTime;

    public String getResourceId() {
        return ledResource != null ? ledResource.getResourceId() : null;
    }
    //这里函数名只能为getUserId,不然调用不了这个函数,它实际上返回的是userName
    public String getUserId() {
        return user != null ? user.getName() : null;
    }

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


