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
    private UserRepository userService;
    @GetMapping("/login")
    public String getData(@RequestParam String param1, @RequestParam String param2) {
        User user=userService.findByUserIdAndPassword(param1,param2);
        if (user!= null) {
            return "User found";
        } else {
            return "User not found";
        }
    }
    @PostMapping("/insert")
    public String insertUser(@RequestBody User user) {
        user.setPermissionId("0");
        user.setAvatarWebUrl("123");
        userService.save(user);
        return "User inserted successfully";
    }
    @PostMapping("/login1")
    public ResponseEntity<User> login(@RequestParam String userId, @RequestParam String password) {
        User user = userService.findByUserIdAndPassword(userId, password);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}