package com.duplexlearn.dao.impl;

import com.duplexlearn.dao.PUserDAO;
import com.duplexlearn.model.po.PUserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 预注册用户的数据访问接口的默认实现
 *
 * 使用 Redis 数据库保存预注册用户，自动设置过期时间
 * 使用 Java 内置的 UUID 生成器生成 UUID
 *
 * @author LoveLonelyTime
 */
@Repository
public class PUserDAOImpl implements PUserDAO {
    /**
     * 预注册用户的过期时间，默认为 15 分钟
     */
    private static final long PUSER_TIMEOUT = 15;

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public PUserDAOImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public PUserPO save(PUserPO pUserPO) {
        // 生成 UUID
        String uuid = UUID.randomUUID().toString();
        pUserPO.setUuid(uuid);

        // 保存到 Redis 数据库
        stringRedisTemplate.opsForValue().set(pUserPO.getEmail(), uuid, PUSER_TIMEOUT, TimeUnit.MINUTES);

        return pUserPO;
    }

    @Override
    public Optional<PUserPO> findByEmail(String email) {
        // 从数据获取 UUID
        String uuid = stringRedisTemplate.opsForValue().get(email);

        // 构建 PO 对象
        PUserPO pUserPO = null;
        if(uuid != null)
        {
            pUserPO = new PUserPO();
            pUserPO.setEmail(email);
            pUserPO.setUuid(uuid);
            stringRedisTemplate.delete(email);
        }

        return Optional.ofNullable(pUserPO);
    }

    @Override
    public void delete(PUserPO pUserPO) {
        // 删除键
        stringRedisTemplate.delete(pUserPO.getEmail());
    }
}
