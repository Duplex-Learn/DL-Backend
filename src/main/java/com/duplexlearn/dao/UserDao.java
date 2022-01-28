package com.duplexlearn.dao;

import com.duplexlearn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The user dao.
 *
 * @author LoveLonelyTime
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * Find by email
     *
     * @param email email
     * @return user
     */
    Optional<User> findByEmail(String email);
}
