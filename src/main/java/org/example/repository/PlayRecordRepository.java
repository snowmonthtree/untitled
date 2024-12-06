package org.example.repository;

import org.example.enity.LedResource;
import org.example.enity.User;
import org.example.enity.PlayRecord;
import org.example.enity.PlayRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PlayRecordRepository extends JpaRepository<PlayRecord, String>{
    // 根据用户ID获取用户的播放记录
    List<PlayRecord> findByUser_UserId(String userId);
    // 统计某个resourceId的所有播放记录数量
    @Query("SELECT COUNT(pr) FROM PlayRecord pr WHERE pr.id.resourceId = :resourceId")
    Long countByResourceId(String resourceId);


}
