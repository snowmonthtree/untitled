package org.example.enity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
@Table(name = "Led_Resource")
public class LedResource {

    @Id
    @Column(name = "Resource_ID", length = 11, nullable = false)
    private String resourceId;

    @ManyToOne
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;  // 假设存在 User 类，如果没有，可以根据实际情况调整

    @Column(name = "Pixel_Size", length = 10, nullable = false)
    private String pixelSize;

    @Column(name = "Download_Count", nullable = false)
    private int downloadCount;

    @Column(name = "Resource_Web_URL", length = 255, nullable = false)
    private String resourceWebUrl;

    @Column(name = "Display_Type", length = 10, nullable = false)
    private String displayType;

    @Column(name = "name", length = 11, nullable = false)
    private String name;

    @Column(name = "Likes", nullable = false)
    private int likes;

    @Column(name = "Detail", length = 255, nullable = false)
    private String detail;

    @Column(name = "View_Web_URL", length = 255, nullable = false)
    private String viewWebUrl;

    @Column(name = "Comment_Num", nullable = false)
    private int commentNum;

    @Column(name = "Up_Time", nullable = false)
    private Timestamp upTime;

    // Getters and Setters
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(String pixelSize) {
        this.pixelSize = pixelSize;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getResourceWebUrl() {
        return resourceWebUrl;
    }

    public void setResourceWebUrl(String resourceWebUrl) {
        this.resourceWebUrl = resourceWebUrl;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getViewWebUrl() {
        return viewWebUrl;
    }

    public void setViewWebUrl(String viewWebUrl) {
        this.viewWebUrl = viewWebUrl;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Timestamp getUpTime() {
        return upTime;
    }

    public void setUpTime(Timestamp upTime) {
        this.upTime = upTime;
    }

    @Override
    public String toString() {
        return "LedResource{" +
                "resourceId='" + resourceId + '\'' +
                ", user=" + user +
                ", pixelSize='" + pixelSize + '\'' +
                ", downloadCount=" + downloadCount +
                ", resourceWebUrl='" + resourceWebUrl + '\'' +
                ", displayType='" + displayType + '\'' +
                ", name='" + name + '\'' +
                ", likes=" + likes +
                ", detail='" + detail + '\'' +
                ", viewWebUrl='" + viewWebUrl + '\'' +
                ", commentNum=" + commentNum +
                ", upTime=" + upTime +
                '}';
    }
}
