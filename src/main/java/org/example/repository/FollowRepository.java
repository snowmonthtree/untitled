package org.example.repository;

import org.example.entity.Follow;
import org.example.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId>{
    @Query("SELECT f FROM Follow f WHERE f.id.followerId=:userId")
    List<Follow> findByFollowerId(String userId);
    @Query("SELECT f FROM Follow f WHERE f.id.followingId=:userId")
    List<Follow> findByFollowingId(String userId);
    @Query("SELECT f FROM Follow f WHERE f.id.followerId=:followerId AND f.id.followingId=:followingId")
    Follow findByFollowerIdAndFollowingId(String followerId, String followingId);
}
