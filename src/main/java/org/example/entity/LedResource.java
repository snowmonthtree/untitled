package org.example.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Led_Resource")
public class LedResource {

    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringLedResourceIdGenerator")
    @Column(name = "Resource_ID", length = 11, nullable = false)
    private String resourceId;

    @ManyToOne
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;  // 假设存在 User 类，如果没有，可以根据实际情况调整

    @Column(name = "Pixel_Size", length = 10, nullable = false)
    private String pixelSize= "0";

    @Column(name = "Download_Count", nullable = false)
    private int downloadCount=0;

    @Column(name = "Resource_Web_URL", length = 255, nullable = false)
    private String resourceWebUrl = "http://default.url";

    @Column(name = "Display_Type", length = 10, nullable = false)
    private String displayType= "default";

    @Column(name = "name", length = 11, nullable = false)
    private String name= "default";

    @Column(name = "Likes", nullable = false)
    private int likes=0;

    @Column(name = "Playback_Volume", nullable = false)
    private int playbackVolume=0;

    @Column(name = "Detail", length = 255, nullable = false)
    private String detail= "default";

    @Column(name = "View_Web_URL", length = 255, nullable = false)
    private String viewWebUrl= "default";

    @Column(name = "Comment_Num", nullable = false)
    private int commentNum=0;

    @Column(name = "Up_Time", nullable = false)
    private Timestamp upTime= new Timestamp(System.currentTimeMillis());


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

    public int getPlaybackVolume() {return playbackVolume;}

    public void setPlaybackVolume(int playbackVolume) {this.playbackVolume = playbackVolume; }

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
