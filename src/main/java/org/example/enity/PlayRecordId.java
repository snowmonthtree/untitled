package org.example.enity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class PlayRecordId implements java.io.Serializable {
    private static final long serialVersionUID = -4221582231141875123L;
    @Column(name = "User_ID", nullable = false, length = 11)
    private String userId;

    @Column(name = "Resource_ID", nullable = false, length = 11)
    private String resourceId;

    @Column(name = "Play_time", nullable = false)
    private Timestamp playTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Timestamp getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Timestamp playTime) {
        this.playTime = playTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlayRecordId entity = (PlayRecordId) o;
        return Objects.equals(this.resourceId, entity.resourceId) &&
                Objects.equals(this.playTime, entity.playTime) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, playTime, userId);
    }

}