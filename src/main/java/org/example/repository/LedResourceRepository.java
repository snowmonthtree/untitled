package org.example.repository;

import org.example.enity.LedResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedResourceRepository extends JpaRepository<LedResource, String> {

    // 通过 Resource_ID 查找 LedResource
    LedResource findByResourceId(String resourceId);

    // 可以添加其他自定义查询方法，例如根据 User_ID 查找所有的资源
    List<LedResource> findByUser_UserIdContainingOrNameContaining(String userId, String name);

    // 获取前 8 个记录
    @Query("SELECT l FROM LedResource l ORDER BY l.upTime DESC")
    List<LedResource> findTop8ByOrderByUpTimeDesc();

    /*
     如果想添加更多的复杂查询，可以使用 JPQL 或者原生 SQL，例如：
     @Query("SELECT l FROM LedResource l WHERE l.likes > :likes")
     List<LedResource> findByLikesGreaterThan(@Param("likes") int likes);
    */
}
