package org.example.repository;

import org.example.entity.AppLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppLoginLogRepository extends JpaRepository<AppLoginLog, String>{
    AppLoginLog findByAppLoginLogId(String appLoginLogId);
    List<AppLoginLog> findByUser_UserId(String userId);
    List<AppLoginLog> findByOrderByAppLoginLogTimeDesc();
    @Query("SELECT a FROM AppLoginLog a WHERE a.appLoginLogTime BETWEEN :startDate AND :endDate")
    List<AppLoginLog> findByAppLoginLogTimeBetween(Date startDate, Date endDate);
}
