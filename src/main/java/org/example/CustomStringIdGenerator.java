package org.example;
import org.example.enity.User;
import org.example.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

public class CustomStringIdGenerator implements IdentifierGenerator {
    private UserRepository userRepository;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Random random=new Random();
        int uuid = random.nextInt(100000000+1);
        String uuidStr =Integer.toString(uuid);
        User user;
        user=userRepository.findByUserId(uuidStr);
        while( user!=null){
            uuid = random.nextInt(100000000+1);
            uuidStr =Integer.toString(uuid);
            user=userRepository.findByUserId(uuidStr);
        }
        return uuidStr;
    }
}