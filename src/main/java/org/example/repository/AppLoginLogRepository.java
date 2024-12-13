package org.example.repository;
import org.example.enity.AppLoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppLoginLogRepository extends JpaRepository<AppLoginLog, String>{
    AppLoginLog findByAppLoginLogId(String appLoginLogId);
}
