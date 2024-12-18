package org.example.enity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "follow", schema = "spot")
public class Follow {
    @EmbeddedId
    private FollowId id;

    @MapsId("followerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Follower_ID", nullable = false)
    private User follower;

    @MapsId("followingId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Following_ID", nullable = false)
    private User following;

    @Column(name = "Status", nullable = false, length = 11)
    private String status;

    @Column(name = "Follow_Time", nullable = false)
    private Instant followTime;

    public FollowId getId() {
        return id;
    }

    public void setId(FollowId id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Instant followTime) {
        this.followTime = followTime;
    }

}