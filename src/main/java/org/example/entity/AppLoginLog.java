package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "app_login_log")
public class AppLoginLog {
    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringAppLoginLogIdGenerator")
    @Column(name = "app_login_log_id", nullable = false, length = 11)
    private String appLoginLogId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;

    @Column(name = "app_login_log_time", nullable = false)
    private Timestamp appLoginLogTime;

    public AppLoginLog() {}

    public String getAppLoginLogId() {
        return appLoginLogId;
    }

    public void setAppLoginLogId(String appLoginLogId) {
        this.appLoginLogId = appLoginLogId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getAppLoginLogTime() {
        return appLoginLogTime;
    }

    public void setAppLoginLogTime(Timestamp appLoginLogTime) {
        this.appLoginLogTime = appLoginLogTime;
    }

}