package org.example.service;

import org.example.entity.Follow;
import org.example.entity.FollowId;
import org.example.entity.User;
import org.example.repository.FollowRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService{
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Follow> getUserFollowers(String userId) {
        return followRepository.findByFollowerId(userId);
    }
    @Override
    public List<Follow> getUserFollowings(String userId) {
        return followRepository.findByFollowingId(userId);
    }
    @Override
    public String addFollow(String followerId, String followingId) {
        if (followerId.equals(followingId)){
            return "不能关注自己";
        }
        Follow beforeFollow=followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        if (beforeFollow!=null){
            followRepository.delete(beforeFollow);
            return "取消关注成功";
        }
        User follower = userRepository.findByUserId(followerId);
        User following = userRepository.findByUserId(followingId);
        Follow follow = new Follow();
        FollowId id = new FollowId();
        id.setFollowerId(followerId);
        id.setFollowingId(followingId);
        follow.setId(id);
        follow.setFollower(follower);
        follow.setFollowing(following);
        follow.setStatus("follow");
        follow.setFollowTime(new Timestamp(System.currentTimeMillis()));
        followRepository.save(follow);
        return "关注成功";
    }
    @Override
    public Boolean userIfFollow(String followerId, String followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        if (follow==null){
            return false;
        }
        return true;
    }
}
