package com.duplexlearn.dao;

import com.duplexlearn.model.po.PUserPO;

import java.util.Optional;

/**
 * 预注册用户的数据访问接口类
 *
 * @author LoveLonelyTime
 */
public interface PUserDAO {
    /**
     * 创建一个预注册对象，将自动设置 UUID
     *
     * @param pUser 预注册对象
     * @return 返回保存在数据库中的预注册对象
     */
    PUserPO save(PUserPO pUser);

    /**
     * 通过邮箱获取一个预注册对象
     *
     * @param email 用户的邮箱
     * @return 预注册对象
     */
    Optional<PUserPO> findByEmail(String email);

    /**
     * 删除一个预注册对象，如果存在的话
     *
     * @param pUserPO 预注册用户
     */
    void delete(PUserPO pUserPO);
}
