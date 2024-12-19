package org.example.service;

import org.example.entity.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService{
    User getUser(String param1, String param2);
    String insertUser(User user, String Code);
    String changePassword(String email,String newPassword,String code);
    String getCode(String email);
    Resource getAvatar(String userId) throws IOException ;
    String imageUpload(User user, MultipartFile fileUpload);
}
