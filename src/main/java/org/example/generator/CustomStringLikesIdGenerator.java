package org.example.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;


@Component
public class CustomStringLikesIdGenerator implements IdentifierGenerator {

    // 生成唯一的 userId
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // 生成一个随机的  ID
        UUID uuid=UUID.randomUUID();
        String likesId = uuid.toString();
        likesId=likesId.substring(0,10);
        boolean isUnique = false;

        while (!isUnique) {
            uuid=UUID.randomUUID();
            likesId = uuid.toString();
            likesId=likesId.substring(0,10);
            isUnique = checkLikesIdUnique(likesId, session); // 检查是否唯一
        }

        return likesId;
    }

    private boolean checkLikesIdUnique(String likesId, SharedSessionContractImplementor session) {
        // 使用 Hibernate 查询来检查 likesId 是否已经存在
        String hql = "SELECT COUNT(c) FROM Likes c WHERE c.likesId = :likesId";
        long count = (long) session.createQuery(hql)
                .setParameter("likesId", likesId)  // 这里参数名应为 likesId
                .getSingleResult();

        return count == 0;  // 如果 count 为 0，说明没有重复，返回 true
    }

}