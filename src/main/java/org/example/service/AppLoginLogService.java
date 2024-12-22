package org.example.service;

import org.example.entity.AppLoginLog;

import java.util.Date;
import java.util.List;

public interface AppLoginLogService {
    List<AppLoginLog> OrderByAppLoginLogTimeDesc();
    int countMonthlyLogins(Date date);
    int countDailyLogins(Date date);
}
