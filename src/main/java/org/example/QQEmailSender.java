package org.example;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;

public class QQEmailSender extends EmailSender {

    public String getTo() {
        return to;
    }

    String to;  // 替换为你想发送的收件人

    // 发件人的电子邮件地址（你的QQ邮箱）
    String from = "2680340431@qq.com";  // 替换为你的QQ邮箱

    // QQ邮箱的SMTP服务器地址
    String host = "smtp.qq.com";
    int yanZhengMa = 0;

    // 设置系统属性
    Properties properties = new Properties();

    // 设置邮件服务器
    public void init(String to) {
        this.to = to;
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");  // 使用SSL端口465
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");  // 启用SSL
    }

    public String sendmail() {
        Random random = new Random();
        yanZhengMa = random.nextInt(90000) + 10000;
        String text = "" + yanZhengMa;
        // 获取 Session 对象并配置身份验证
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 使用你的QQ邮箱账户和授权码进行认证
                //mxhjwwwykbrceacb
                return new PasswordAuthentication("2680340431@qq.com", "mxhjwwwykbrceacb");
            }
        });

        try {
            // 创建一个 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // 设置发件人
            message.setFrom(new InternetAddress(from));

            // 设置收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // 设置邮件主题
            message.setSubject("Test Email from Jakarta Mail");

            // 设置邮件内容
            message.setText(text);

            // 发送邮件
            Transport.send(message);
            System.out.println("Email sent successfully...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return text;
    }

    public boolean checkEmail(String email) {
        email = email.substring(1, email.length() - 1);
        String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        if (email == null || email.isEmpty()) {
            return false;
        }
        // 匹配邮箱格式
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }
}