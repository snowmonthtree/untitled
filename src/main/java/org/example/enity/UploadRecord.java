package org.example.enity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "upload_record", schema = "spot")
public class UploadRecord {
    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringUploadRecordIdGenerator")
    @Column(name = "UpRecord_ID", nullable = false, length = 11)
    private String uprecordId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Resource_ID", nullable = false)
    private LedResource resource;

    @Column(name = "Upload_Time", nullable = false)
    private Timestamp uploadTime;

    public String getUprecordId() {
        return uprecordId;
    }

    public void setUprecordId(String uprecordId) {
        this.uprecordId = uprecordId;
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

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

}