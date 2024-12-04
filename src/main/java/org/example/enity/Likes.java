package org.example.enity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "likes")
public class Likes {
    @Id
    @Column(name = "Likes_ID", nullable = false, length = 11)
    private String likesId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Resource_ID", nullable = false)
    private LedResource resource;

    @Column(name = "Like_Time", nullable = false)

    private Instant likeTime;

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

    public LedResource getResource() {
        return resource;
    }

    public void setResource(LedResource resource) {
        this.resource = resource;
    }

    public Instant getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Instant likeTime) {
        this.likeTime = likeTime;
    }



}