package org.example.service;

import org.example.EmailSender;
import org.example.QQEmailSender;
import org.example.entity.AppLoginLog;
import org.example.entity.User;
import org.example.repository.AppLoginLogRepository;
import org.example.repository.UserRepository;
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
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private static final String AVATARIMAGE_DIRECTORY = "C:\\Users\\Administrator\\Pictures\\USER\\Avatar";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppLoginLogRepository appLoginLogRepository;
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
    public String insertUser(User user,  String Code) {
        if (!Code.equals(temp)){
            return "验证码错误";
        }
        user.setPermissionId("0");
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "User inserted successfully";
    }
    @Override
    public String changePassword( String email, String newPassword, String code){
        User user1=userRepository.findByEmail(email);
        if (user1==null){
            return "用户不存在";
        } else if (!code.equals(temp)) {
            return "验证码错误";
        } else {
            try {
                user1.setPassword(newPassword);
                userRepository.save(user1);
            }catch (Exception e){
                return "失败"+e.getMessage();
            }
        }
        return "success";
    }
    //目前最多只能支持一个用户同时进行发送验证码并修改密码或注册
    @Override
    public String getCode( String email){
        EmailSender emailSender=new QQEmailSender();
        emailSender.init(email);
        temp=emailSender.sendmail();
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
}
