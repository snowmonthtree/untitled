package org.example.service;

import org.example.entity.Follow;

import java.util.List;

public interface FollowService {
    List<Follow> getUserFollowers(String follwerId);
    List<Follow> getUserFollowings(String followingId);
    String addFollow(String followerId, String followingId);
    Boolean userIfFollow(String followerId, String followingId);
}
