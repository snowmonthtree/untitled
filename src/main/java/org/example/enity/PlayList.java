package org.example.enity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Entity
@Table(name = "play_list")
public class PlayList {
    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringPlayListIdGenerator")
    @Column(name = "Playlist_ID", nullable = false, length = 11)
    private String playlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID")
    private User user;

    @Column(name = "Playlist_name", nullable = false, length = 50)
    private String playlistName;

    @Column(name = "Create_Time", nullable = false)
    private Timestamp createTime;

    @Column(name = "Playlist_Type", nullable = false, length = 20)
    private String playlistType;

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getPlaylistType() {
        return playlistType;
    }

    public void setPlaylistType(String playlistType) {
        this.playlistType = playlistType;
    }

}