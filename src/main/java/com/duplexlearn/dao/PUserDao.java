package com.duplexlearn.dao;

import java.util.Optional;

/**
 * PUserDao.
 *
 * @author LoveLonelyTime
 */
public interface PUserDao {
    /**
     * Create a puser.
     *
     * @param email User's email
     * @return PUser's UUID
     */
    String createPUser(String email);

    /**
     * Get puser uuid.
     *
     * @param email User's email
     * @return PUser's UUID
     */
    Optional<String> getPUserUUIDByEmail(String email);
}
