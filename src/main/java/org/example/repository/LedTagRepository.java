package org.example.repository;

import org.example.entity.LedTag;
import org.example.entity.LedTagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LedTagRepository extends JpaRepository<LedTag, LedTagId> {

    // 根据 resourceId 查找所有 LedTag
    List<LedTag> findByIdResourceId(String resourceId);

    // 根据 tagId 查找所有 LedTag
    List<LedTag> findByIdTagId(String tagId);
    // 使用 @Query 注解来显式地定义查询语句
    @Query("SELECT lt FROM LedTag lt WHERE lt.id.resourceId = :resourceId AND lt.id.tagId = :tagId")
    LedTag findByIdResourceIdAndTagId(@Param("resourceId") String resourceId, @Param("tagId") String tagId);
}