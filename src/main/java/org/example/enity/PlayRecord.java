package org.example.enity;

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
import java.time.Instant;


@Entity
@Table(name = "play_record")
public class PlayRecord {
    @EmbeddedId
    private PlayRecordId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_ID", nullable = false)
    @JsonManagedReference
    private User user;

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Resource_ID", nullable = false)
    @JsonManagedReference
    private LedResource ledResource;

    public PlayRecordId getId() {
        return id;
    }

    public String getResourceId() {
        return ledResource != null ? ledResource.getResourceId() : null;
    }
    //这里函数名只能为getUserId,不然调用不了这个函数,它实际上返回的是userName
    public String getUserId() {
        return user != null ? user.getName() : null;
    }


    public void setId(PlayRecordId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LedResource getLedResource() {
        return ledResource;
    }

    public void setLedResource(LedResource ledResource) {
        this.ledResource = ledResource;
    }
}