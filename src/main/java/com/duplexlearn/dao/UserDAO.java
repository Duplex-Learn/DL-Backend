package com.duplexlearn.dao;

import com.duplexlearn.model.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户的数据访问接口类
 *
 * @author LoveLonelyTime
 */
@Repository
public interface UserDAO extends JpaRepository<UserPO, Long> {
    /**
     * 通过邮箱寻找一个用户
     *
     * @param email 用户的邮箱
     * @return 用户
     */
    Optional<UserPO> findByEmail(String email);
}
