package org.example.controller;
import org.aspectj.apache.bcel.classfile.Code;
import org.example.EmailSender;
import org.example.QQEmailSender;
import org.example.enity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
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
    public String changePassword(@RequestParam String Email,@RequestParam String newPassword,@RequestParam String Code){
        User user1=userRepository.findByEmail(Email);
        if (user1==null){
            return "用户不存在";
        } else if (!Code.equals(temp)) {
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
    public String getCode(@RequestParam String Email){
        EmailSender emailSender=new QQEmailSender();
        emailSender.init(Email);
        temp=emailSender.sendmail();
        return "验证码已发送";
    }

}