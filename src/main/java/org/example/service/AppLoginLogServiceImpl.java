package org.example.service;

import org.example.entity.AppLoginLog;
import org.example.repository.AppLoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AppLoginLogServiceImpl implements AppLoginLogService{
    @Autowired
    AppLoginLogRepository appLoginLogRepository;
    @Override
    public List<AppLoginLog> OrderByAppLoginLogTimeDesc()
    {
        return appLoginLogRepository.findByOrderByAppLoginLogTimeDesc();
    }
    @Override
    public int countMonthlyLogins(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        Date endDate = calendar.getTime();

        List<AppLoginLog> logs = appLoginLogRepository.findByAppLoginLogTimeBetween(startDate, endDate);
        return logs.size();
    }
    @Override
    public int countDailyLogins(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = calendar.getTime();

        List<AppLoginLog> logs = appLoginLogRepository.findByAppLoginLogTimeBetween(startDate, endDate);
        return logs.size();
    }
}
