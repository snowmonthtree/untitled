package org.example.repository;

import org.example.enity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PlayRecordRepository extends JpaRepository<PlayRecord, PlayRecordId>{
    // 根据用户ID获取用户的播放记录，并按时间戳降序排序
    @Query("SELECT pr FROM PlayRecord pr WHERE pr.id.userId = :userId ORDER BY pr.id.playTime DESC")
    List<PlayRecord> findByUser_UserIdOrderByPlayTimeDesc(String userId);

    @Query("SELECT pr FROM PlayRecord pr WHERE pr.id.resourceId = :resourceId")
    List<PlayRecord> findByIdResourceId(@Param("resourceId") String resourceId);

     // 统计某个resourceId的所有播放记录数量
    @Query("SELECT COUNT(pr) FROM PlayRecord pr WHERE pr.id.resourceId = :resourceId")
    Long countByResourceId(String resourceId);

    @Query("SELECT pr FROM PlayRecord pr WHERE pr.id.userId = :userId AND pr.id.resourceId = :resourceId")
    PlayRecord findByUser_UserIdAndLedResource_ResourceId(String userId, String resourceId);
}
