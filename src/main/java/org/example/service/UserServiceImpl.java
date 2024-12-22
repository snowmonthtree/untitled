package org.example.service;

import org.example.EmailSender;
import org.example.QQEmailSender;
import org.example.entity.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{

    private ConcurrentHashMap<String, ExpiringCode> verificationCodes = new ConcurrentHashMap<>();
    private static final String AVATARIMAGE_DIRECTORY = "C:\\Users\\Administrator\\Pictures\\USER\\Avatar";
    private static class ExpiringCode {
        private final String code;
        private final long expirationTime;

        public ExpiringCode(String code, long expirationTime) {
            this.code = code;
            this.expirationTime = expirationTime;
        }

        public String getCode() {
            return code;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppLoginLogRepository appLoginLogRepository;
    @Autowired
    private LedResourceRepository ledResourceRepository;
    @Autowired
    private LedResourceService ledResourceService;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private LedListRepository ledListRepository;

    private String temp;
    @Override
    public User getUser( String param1,  String param2) {
        User user= userRepository.findByEmailAndPassword(param1,param2);
        if(user!=null){
            AppLoginLog appLoginLog =new AppLoginLog();
            appLoginLog.setUser(user);
            appLoginLog.setAppLoginLogTime(new Timestamp(System.currentTimeMillis()));
            appLoginLogRepository.save(appLoginLog);}
        return user;
    }
    @Override
    public String insertUser(User user, String code) {
        String email = user.getEmail(); // 假设 User 类中有 getEmail 方法
        if (!verificationCodes.containsKey(email) || !code.equals(verificationCodes.get(email).getCode())) {
            return "验证码错误";
        }

        user.setPermissionId("0");
        try {
            userRepository.save(user);
            verificationCodes.remove(email); // 验证成功后移除验证码
        } catch (Exception e) {
            return e.getMessage();
        }
        return "User inserted successfully";
    }

    @Override
    public String changePassword(String email, String newPassword, String code) {
        if (!verificationCodes.containsKey(email) || !code.equals(verificationCodes.get(email).getCode())) {
            return "验证码错误";
        }
        User user1 = userRepository.findByEmail(email);
        if (user1 == null) {
            return "用户不存在";
        } else {
            try {
                user1.setPassword(newPassword);
                userRepository.save(user1);
                verificationCodes.remove(email); // 验证成功后移除验证码
            } catch (Exception e) {
                return "失败" + e.getMessage();
            }
        }
        return "Change password successfully";
    }
    @Override
    public String getCode(String email) {
        if (verificationCodes.containsKey(email)) {
            ExpiringCode expiringCode = verificationCodes.get(email);
            if (!expiringCode.isExpired()) {
                return "验证码已发送，请勿频繁请求";
            }
        }

        EmailSender emailSender = new QQEmailSender();
        emailSender.init(email);
        String code = emailSender.sendmail();
        long expirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5); // 5分钟过期
        verificationCodes.put(email, new ExpiringCode(code, expirationTime));
        return "验证码已发送";
    }

    @Override
    public Resource getAvatar(String userId) throws IOException {
        // 根据用户ID构造头像文件路径
        User user = userRepository.findByUserId(userId);
        String url=user.getAvatarWebUrl();
        Path avatarPath = Paths.get(url);
        Resource resource = new UrlResource(avatarPath.toUri());

        if (resource.exists() && resource.isReadable()) {
            // 返回头像内容
            return resource;
        } else {
            return null;
        }
    }
    @Override
    public String imageUpload(User user, MultipartFile fileUpload){
        if (fileUpload.isEmpty()) {
            return "Failed to upload file: File is empty";
        }

        // 使用UUID生成唯一的文件名，并保留文件扩展名
        String originalFileName = fileUpload.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // 创建路径
        File tmp = new File(AVATARIMAGE_DIRECTORY);
        if (!tmp.exists()) {
            tmp.mkdirs();
        }

        // 构建完整的文件路径
        String newFilePath = AVATARIMAGE_DIRECTORY + File.separator +fileName;

        // 删除旧的头像文件（如果存在）
        String oldFilePath = user.getAvatarWebUrl();
        if (oldFilePath != null) {
            File oldFile = new File(oldFilePath);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        // 更新用户头像URL
        user.setAvatarWebUrl(newFilePath);
        userRepository.save(user);

        // 保存文件
        File upFile = new File(newFilePath);
        try {
            fileUpload.transferTo(upFile);
            return "File uploaded successfully";
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }
    @Override
    public String deleteUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "User not found";
        }
        List<Feedback> feedbacks = feedbackRepository.findByUser_UserId(userId);
        List<Follow> followers = followRepository.findByFollowerId(userId);
        List<Follow> following = followRepository.findByFollowingId(userId);
        List<PlayList> playLists=playListRepository.findByUser_UserId(userId);
        List<AppLoginLog> appLoginLogs = appLoginLogRepository.findByUser_UserId(userId);
        for (PlayList playList : playLists) {
            List<LedList> ledLists = ledListRepository.findByIdPlaylistId(playList.getPlaylistId());
            for (LedList ledList : ledLists) {
                ledListRepository.delete(ledList);
            }
        }
        List<LedResource> ledResources = ledResourceRepository.findByUser_UserId(userId);
        for (LedResource ledResource : ledResources) {
            ledResourceService.deleteResource(userId,ledResource.getResourceId());
        }
        if(appLoginLogs!=null){
        appLoginLogRepository.deleteAll(appLoginLogs);
        }
        if (followers!=null) {
            followRepository.deleteAll(followers);
        }
        if (following!=null) {
            followRepository.deleteAll(following);
        }
        if(feedbacks!=null) {
            feedbackRepository.deleteAll(feedbacks);
        }
        if(playLists!=null){
        playListRepository.deleteAll(playLists);
        }
        Path avatarPath = null;
        if (user.getAvatarWebUrl() != null) {
            avatarPath = Paths.get(user.getAvatarWebUrl());
        }
        if (avatarPath.toFile().exists()) {
            try {
                avatarPath.toFile().delete();
            } catch (Exception e) {
                return "Failed to delete avatar file: " + e.getMessage();
            }
        }
        userRepository.delete(user);
        return "User deleted successfully";
    }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAllOrderByName();
    }
}
