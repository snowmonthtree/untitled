package org.example.controller;
import org.example.enity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/login")
    public User getUser(@RequestParam String param1, @RequestParam String param2) {
        User user= userRepository.findByUserEmailAndPassword(param1,param2);
        return user;
    }
    @PostMapping("/insert")
    public String insertUser(@RequestBody User user) {

        try {
            userRepository.save(user);
        } catch (Exception e) {
        return e.getMessage();
        }
        return "User inserted successfully";
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody User user){
        User user1=userRepository.findByUserEmail(user.getEmail());
        if (user1==null){
            return "用户不存在";
        }
        else {
            try {
                userRepository.save(user);
            }catch (Exception e){
                return "失败"+e.getMessage();
            }
        }
        return "success";
    }
    @PostMapping("/login1")
    public ResponseEntity<User> login(@RequestParam String userId, @RequestParam String password) {
        User user = userRepository.findByUserEmailAndPassword(userId, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}