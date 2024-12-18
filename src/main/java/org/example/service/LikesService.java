package org.example.service;

public interface LikesService {
    Boolean userIfLike(String userId, String resourceId);
    String likeResource(String userId, String resourceId);
    Integer getLikesNum(String resourceId) ;
}
