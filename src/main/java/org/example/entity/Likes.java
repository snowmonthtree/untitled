package org.example.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringLikesIdGenerator")
    @Column(name = "Likes_ID", nullable = false, length = 11)
    private String likesId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_ID", nullable = false)
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Resource_ID", nullable = false)
    @JsonManagedReference
    private LedResource ledResource;

    @Column(name = "Like_Time", nullable = false)

    private Timestamp likeTime;

    public String getLikesId() {
        return likesId;
    }

    public void setLikesId(String likesId) {
        this.likesId = likesId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getResourceId() {
        return ledResource != null ? ledResource.getResourceId() : null;
    }
    //这里函数名只能为getUserId,不然调用不了这个函数,它实际上返回的是userName
    public String getUserId() {
        return user != null ? user.getName() : null;
    }

    public LedResource getResource() {
        return ledResource;
    }

    public void setResource(LedResource ledResource) {
        this.ledResource = ledResource;
    }

    public Timestamp getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Timestamp likeTime) {
        this.likeTime = likeTime;
    }



}