package org.example.enity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "customStringGenerator")
    @GenericGenerator(name = "customStringGenerator", strategy = "org.example.generator.CustomStringUserIdGenerator")
    @Column(name = "User_ID", length = 11,unique = true)
    private String userId;

    @Nonnull
    @Column(name = "Name", length = 10)
    private String name;

    @Column(name = "Permission_ID", length = 1)
    private String permissionId="0";

    @Column(name = "Password", length = 13)
    private String password;


    @Column(name = "Email", length = 15,unique = true)
    private String email;

    @Nullable
    @Column(name = "Avatar_Web_URL", length = 255)
    private String avatarWebUrl="123";

    @Column(name = "Likes_Num")
    private int likesNum;

    @Column(name = "Follwers_Num")
    private int followersNum;

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarWebUrl() {
        return avatarWebUrl;
    }

    public void setAvatarWebUrl(String avatarWebUrl) {
        this.avatarWebUrl = avatarWebUrl;
    }

    public int getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(int likesNum) {
        this.likesNum = likesNum;
    }

    public int getFollowersNum() {
        return followersNum;
    }

    public void setFollowersNum(int followersNum) {
        this.followersNum = followersNum;
    }
}
