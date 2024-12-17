package org.example.generator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;


@Component
public class CustomStringUploadRecordIdGenerator implements IdentifierGenerator {

    // 生成唯一的 userId
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // 生成一个随机的  ID
        UUID uuid=UUID.randomUUID();
        String uploadRecordId = uuid.toString();
        uploadRecordId=uploadRecordId.substring(0,10);
        boolean isUnique = false;

        while (!isUnique) {
            uuid=UUID.randomUUID();
            uploadRecordId = uuid.toString();
            uploadRecordId=uploadRecordId.substring(0,10);
            isUnique = checkIdUnique(uploadRecordId, session); // 检查是否唯一
        }

        return uploadRecordId;
    }
    private boolean checkIdUnique(String uploadRecordId, SharedSessionContractImplementor session) {
        // 使用 Hibernate 查询来检查 uploadRecordId 是否已经存在
        String hql = "SELECT COUNT(ur) FROM UploadRecord ur WHERE ur.uprecordId = :uploadRecordId";
        long count = (long) session.createQuery(hql)
                .setParameter("uploadRecordId", uploadRecordId)  // 这里参数名应为 uploadRecordId
                .getSingleResult();

        return count == 0;  // 如果 count 为 0，说明没有重复，返回 true
    }

}