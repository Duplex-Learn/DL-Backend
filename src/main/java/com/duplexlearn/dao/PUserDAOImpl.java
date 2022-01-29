package com.duplexlearn.dao;

import com.duplexlearn.model.PUserPO;
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
public class PUserDAOImpl implements PUserDAO {

    private StringRedisTemplate stringRedisTemplate;

    private static final long PUSER_TIMEOUT = 10 * 60;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public PUserPO save(PUserPO pUserPO) {
        String uuid = UUID.randomUUID().toString();
        pUserPO.setUuid(uuid);
        stringRedisTemplate.opsForValue().set(pUserPO.getEmail(), uuid, PUSER_TIMEOUT, TimeUnit.SECONDS);
        return pUserPO;
    }

    @Override
    public Optional<PUserPO> findByEmail(String email) {
        String uuid = stringRedisTemplate.opsForValue().get(email);
        PUserPO pUserPO = null;
        if(uuid != null)
        {
            pUserPO = new PUserPO();
            pUserPO.setEmail(email);
            pUserPO.setUuid(uuid);
        }
        return Optional.ofNullable(pUserPO);
    }
}
