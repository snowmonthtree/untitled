package org.example.controller;
import org.example.repository.UserRepository;
import org.example.EmailSender;
import org.example.QQEmailSender;
import org.example.enity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;


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
    @PostMapping("/uploadAvatarImage")
    public String uploadAvatarImage(@RequestParam("userId") String userId, @RequestParam("image") byte[] imageData) {
        try {
            // 将字节数组转换为 BufferedImage
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
            // 定义文件路径和名称
            File file = new File(AVATARIMAGE_DIRECTORY + File.separator + userId + ".png");
            // 创建文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            // 将 BufferedImage 写入文件
            ImageIO.write(bufferedImage, "png", fos);
            // 关闭文件输出流
            fos.close();
            User user = userRepository.findByUserId(userId);
            if (user != null) {
                // 设置图像路径
                user.setAvatarWebUrl(file.getAbsolutePath());
                // 保存用户对象
                userRepository.save(user);
            }

            return "Image saved successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to save image: " + e.getMessage();
        }
    }
}