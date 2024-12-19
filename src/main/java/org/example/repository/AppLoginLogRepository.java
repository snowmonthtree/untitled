package org.example.repository;
import org.example.entity.AppLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppLoginLogRepository extends JpaRepository<AppLoginLog, String>{
    AppLoginLog findByAppLoginLogId(String appLoginLogId);
    List<AppLoginLog> findByUser_UserId(String userId);
}
