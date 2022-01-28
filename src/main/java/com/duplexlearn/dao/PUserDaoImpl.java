package com.duplexlearn.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of puserdao.
 * <p>
 * Use redis database to save puser.
 *
 * @author LoveLonelyTime
 */
@Repository
public class PUserDaoImpl implements PUserDao {

    private StringRedisTemplate stringRedisTemplate;

    private static final long PUSER_TIMEOUT = 10 * 60;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String createPUser(String email) {
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(email, uuid, PUSER_TIMEOUT, TimeUnit.SECONDS);
        return uuid;
    }

    @Override
    public Optional<String> getPUserUUIDByEmail(String email) {
        String value = stringRedisTemplate.opsForValue().get(email);
        return Optional.ofNullable(value);
    }
}
