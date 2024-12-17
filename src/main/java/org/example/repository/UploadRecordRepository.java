package org.example.repository;

import org.example.enity.UploadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadRecordRepository extends JpaRepository<UploadRecord, String> {
    @Query("SELECT ur FROM UploadRecord ur WHERE ur.user.userId = :userId ORDER BY ur.uploadTime DESC")
    List<UploadRecord> findByUser_UserId(String userId);

    List<UploadRecord> findByResource_ResourceId(String resourceId);
}