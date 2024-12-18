package org.example.service;

import org.example.enity.LedResource;
import org.example.enity.Likes;
import org.example.repository.LedResourceRepository;
import org.example.repository.LikesRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class LikesServiceImpl implements LikesService{
    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private LedResourceRepository ledResourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean userIfLike(String userId, String resourceId) {
        Optional<Likes> optionalLikes = likesRepository.findByUser_UserIdAndLedResource_ResourceId(userId, resourceId);
        return optionalLikes.isPresent();
    }


    @Override
    public String likeResource(String userId, String resourceId) {
        try {
            Optional<Likes> optionalLikes = likesRepository.findByUser_UserIdAndLedResource_ResourceId(userId, resourceId);
            if (optionalLikes.isPresent()) {
                likesRepository.delete(optionalLikes.get());
                return "取消点赞";
            } else {
                Likes likes = new Likes();
                likes.setUser(userRepository.findByUserId(userId));
                likes.setResource(ledResourceRepository.findByResourceId(resourceId));
                likes.setLikeTime(new Timestamp(System.currentTimeMillis()));
                likesRepository.save(likes);
                return "点赞成功";
            }
        } catch (Exception e) {
            return "Error liking resource: " + e.getMessage();
        }
    }
    public Integer getLikesNum(String resourceId) {
        long count = likesRepository.countByResourceId(resourceId);
        LedResource ledResource = ledResourceRepository.findByResourceId(resourceId);
        if (ledResource != null) {
            ledResource.setLikes((int) count);
            ledResourceRepository.save(ledResource); // 保存更新后的实体
        }
        return (int) count;
    }
}
