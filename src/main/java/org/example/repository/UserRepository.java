package org.example.repository;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    // 可以添加自定义查询方法
    User findByEmailAndPassword(String userEmail, String password);
    User findByEmail(String userEmail);
    User findByUserId(String userId);
    @Query("SELECT u FROM User u WHERE u.permissionId ='0' ORDER BY u.name ASC")
    List<User> findAllOrderByName();
}
