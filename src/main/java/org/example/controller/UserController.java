package org.example.controller;
import org.example.repository.UserRepository;
import org.example.EmailSender;
import org.example.QQEmailSender;
import org.example.enity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;



@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final String AVATARIMAGE_DIRECTORY = "C:\\Users\\Administrator\\Pictures\\USER\\Avatar";
    @Autowired
    private UserRepository userRepository;
    private String temp;
    @GetMapping("/login")
    public User getUser(@RequestParam String param1, @RequestParam String param2) {
        User user= userRepository.findByEmailAndPassword(param1,param2);
        return user;
    }
    @PostMapping("/insert")
    public String insertUser(@RequestBody User user,@RequestParam String Code) {
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
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String email,@RequestParam String newPassword,@RequestParam String code){
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
    @GetMapping("/getCode")
    public String getCode(@RequestParam String email){
        EmailSender emailSender=new QQEmailSender();
        emailSender.init(email);
        temp=emailSender.sendmail();
        return "验证码已发送";
    }
//    @PostMapping("/uploadAvatar")
//    public String uploadAvatar(@RequestParam("userId") String userId, @RequestParam("file") MultipartFile file) {
//        try {
//            // 检查文件是否为空
//            if (file.isEmpty()) {
//                return "文件为空";
//            }
//
//            // 获取用户对象
//            User user = userRepository.findById(userId).orElse(null);
//            if (user == null) {
//                return "用户不存在";
//            }
//
//            // 读取文件内容
//            byte[] bytes = file.getBytes();
//
//            // 将字节数组转换为BufferedImage
//            BufferedImage image = ImageIO.read(file.getInputStream());
//
//            // 将BufferedImage转换为Bitmap
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(image, "bmp", baos);
//            byte[] bitmapBytes = baos.toByteArray();
//
//            // 设置用户的头像
//            user.setAvatar(bitmapBytes);
//
//            // 保存用户对象
//            userRepository.save(user);
//
//            return "头像上传成功";
//        } catch (IOException e) {
//            return "上传失败: " + e.getMessage();
//        }
//    }
    @GetMapping("/avatar/{userId}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String userId) throws IOException {
        // 根据用户ID构造头像文件路径
        User user = userRepository.findByUserId(userId);
        Path avatarPath =Paths.get(user.getAvatarWebUrl());
        Resource resource = new UrlResource(avatarPath.toUri());

        if (resource.exists() && resource.isReadable()) {
            // 返回头像内容
            return ResponseEntity.ok().body(resource);
        } else {
            // 如果头像不存在或不可读，返回 404 错误
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/uploadFile")
    public String imageUpload(@RequestParam("userId") String userId,@RequestParam("file") MultipartFile fileUpload) {
        // 获取文件名
        //String fileName = fileUpload.getOriginalFilename();
        String tmpFilePath = "C:\\Users\\Administrator\\Pictures\\USER\\Avatar";

        // 没有路径就创建路径
        File tmp = new File(tmpFilePath);
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        String resourcesPath = tmpFilePath + "//" + userId;
        User user=userRepository.findByUserId(userId);
        user.setAvatarWebUrl(resourcesPath);
        File upFile = new File(resourcesPath);
        try {
            fileUpload.transferTo(upFile);
            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }
}