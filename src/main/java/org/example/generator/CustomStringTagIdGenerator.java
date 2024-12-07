package org.example.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;


@Component
public class CustomStringTagIdGenerator implements IdentifierGenerator {

    // 生成唯一的 userId
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // 生成一个随机的  ID
        UUID uuid=UUID.randomUUID();
        String tagId = uuid.toString();
        tagId=tagId.substring(0,10);
        boolean isUnique = false;

        while (!isUnique) {
            uuid=UUID.randomUUID();
            tagId = uuid.toString();
            tagId=tagId.substring(0,10);
            isUnique = checkLikesIdUnique(tagId, session); // 检查是否唯一
        }

        return tagId;
    }

    private boolean checkLikesIdUnique(String tagId, SharedSessionContractImplementor session) {
        // 使用 Hibernate 查询来检查 tagId 是否已经存在
        String hql = "SELECT COUNT(t) FROM Tag t WHERE t.tagId = :tagId";
        long count = (long) session.createQuery(hql)
                .setParameter("tagId", tagId)  // 这里参数名应为 tagId
                .getSingleResult();

        return count == 0;  // 如果 count 为 0，说明没有重复，返回 true
    }

}