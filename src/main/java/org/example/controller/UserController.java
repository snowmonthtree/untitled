package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public User getUser(@RequestParam String param1, @RequestParam String param2) {
        return userService.getUser(param1,param2);
    }
    @PostMapping("/insert")
    public String insertUser(@RequestBody User user,@RequestParam String Code) {
        return userService.insertUser(user,Code);
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String email,@RequestParam String newPassword,@RequestParam String code){
        return userService.changePassword(email,newPassword,code);
    }
    //目前最多只能支持一个用户同时进行发送验证码并修改密码或注册
    @GetMapping("/getCode")
    public String getCode(@RequestParam String email){
        return userService.getCode(email);
    }

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String userId) throws IOException {
        return ResponseEntity.ok(userService.getAvatar(userId));
    }
    @PatchMapping("/uploadFile")
    public String imageUpload(@RequestPart("user") User user, @RequestParam("file") MultipartFile fileUpload){
        return userService.imageUpload(user,fileUpload);
    }

}