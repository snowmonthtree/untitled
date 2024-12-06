package org.example.repository;

import org.example.enity.LedResource;
import org.example.enity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, String>{
    // 通过 Resource_ID 查找 Likes
    List<Likes> findByLedResource_ResourceId(String resourceId);

    //查询 Usr是否点过赞
    Optional<Likes> findByUser_UserIdAndLedResource_ResourceId(String userId, String resourceId);
}
