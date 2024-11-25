package org.example.repository;
import org.example.enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 可以添加自定义查询方法
    User findByUserEmailAndPassword(String userEmail, String password);
    User findByUserEmail(String userEmail);
    User findByUserId(String userId);
}
