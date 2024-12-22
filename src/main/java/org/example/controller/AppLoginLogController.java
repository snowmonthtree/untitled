package org.example.controller;

import org.example.entity.AppLoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/apploginlog")
public class AppLoginLogController {
    @Autowired
    private org.example.service.AppLoginLogService AppLoginLogService;
    @RequestMapping("/get")
    public List<AppLoginLog> getAppLoginLog()
    {
        return AppLoginLogService.OrderByAppLoginLogTimeDesc();
    }
    @RequestMapping("/get-monthly-logins")
    public int getMonthlyLogins(String date)
    {
        return AppLoginLogService.countMonthlyLogins(java.sql.Date.valueOf(date));
    }
    @RequestMapping("/get-daily-logins")
    public int getDailyLogins(String date)
    {
        return AppLoginLogService.countDailyLogins(java.sql.Date.valueOf(date));
    }
}
