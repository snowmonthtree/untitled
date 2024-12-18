package org.example.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;


@Component
public class CustomStringCommentIdGenerator implements IdentifierGenerator {

    // 生成唯一的 userId
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // 生成一个随机的  ID
        UUID uuid=UUID.randomUUID();
        String commentId = uuid.toString();
        commentId=commentId.substring(0,10);
        boolean isUnique = false;

        while (!isUnique) {
            uuid=UUID.randomUUID();
            commentId = uuid.toString();
            commentId=commentId.substring(0,10);
            isUnique = checkIdUnique(commentId, session); // 检查是否唯一
        }

        return commentId;
    }

    private boolean checkIdUnique(String commentId, SharedSessionContractImplementor session) {
        // 使用 Hibernate 查询来检查 commentId 是否已经存在
        String hql = "SELECT COUNT(c) FROM Comment c WHERE c.commentId= :commentId";
        long count = (long) session.createQuery(hql)
                .setParameter("commentId", commentId)  // 这里参数名应为 commentId
                .getSingleResult();

        return count == 0;  // 如果 count 为 0，说明没有重复，返回 true
    }

}