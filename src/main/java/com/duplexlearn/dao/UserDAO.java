package com.duplexlearn.dao;

import com.duplexlearn.model.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The user dao.
 *
 * @author LoveLonelyTime
 */
@Repository
public interface UserDAO extends JpaRepository<UserPO, Long> {
    /**
     * Find by email
     *
     * @param email email
     * @return user
     */
    Optional<UserPO> findByEmail(String email);
}
