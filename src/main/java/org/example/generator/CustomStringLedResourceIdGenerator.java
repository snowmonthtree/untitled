package org.example.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;


@Component
public class CustomStringLedResourceIdGenerator implements IdentifierGenerator {

    // 生成唯一的 userId
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // 生成一个随机的  ID
        UUID uuid=UUID.randomUUID();
        String resourceId = uuid.toString();
        resourceId=resourceId.substring(0,10);
        boolean isUnique = false;

        while (!isUnique) {
            uuid=UUID.randomUUID();
            resourceId = uuid.toString();
            resourceId=resourceId.substring(0,10);
            isUnique = checkIdUnique(resourceId, session); // 检查是否唯一
        }

        return resourceId;
    }

    private boolean checkIdUnique(String resourceId, SharedSessionContractImplementor session) {
        // 使用 Hibernate 查询来检查 resourceId 是否已经存在
        String hql = "SELECT COUNT(l) FROM LedResource l WHERE l.resourceId= :resourceId";
        long count = (long) session.createQuery(hql)
                .setParameter("resourceId", resourceId)  // 这里参数名应为 resourceId
                .getSingleResult();

        return count == 0;  // 如果 count 为 0，说明没有重复，返回 true
    }

}