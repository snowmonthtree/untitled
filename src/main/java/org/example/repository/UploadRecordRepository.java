package org.example.repository;

import org.example.enity.UploadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadRecordRepository extends JpaRepository<UploadRecord, String> {
    List<UploadRecord> findByUser_UserId(String userId);
}