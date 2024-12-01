package org.example;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Random;


@Component
public class CustomStringIdGenerator implements IdentifierGenerator {

    // 生成唯一的 userId
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // 生成一个随机的 11 位数字 ID
        String userId=null;
        boolean isUnique = false;

        while (!isUnique) {
            userId = generateRandomUserId();  // 生成随机的 userId
            isUnique = checkUserIdUnique(userId, session); // 检查是否唯一
        }

        return userId;
    }

    // 生成一个随机的 userId（例如：11 位数字）
    private String generateRandomUserId() {
        Random random = new Random();
        long randomLong = (long) (random.nextDouble() * 1e11); // 生成一个 11 位的数字
        return String.format("%011d", randomLong); // 保证是 11 位数字，前面补 0
    }

    // 检查 userId 是否唯一
    private boolean checkUserIdUnique(String userId, SharedSessionContractImplementor session) {
        // 使用 Hibernate 查询来检查 userId 是否已经存在
        String hql = "SELECT COUNT(u) FROM User u WHERE u.userId = :userId";
        long count = (long) session.createQuery(hql)
                .setParameter("userId", userId)
                .getSingleResult();

        return count == 0;  // 如果 count 为 0，说明没有重复，返回 true
    }
}
