package com.duplexlearn.dao;

import com.duplexlearn.model.PUserPO;

import java.util.Optional;

/**
 * PUserDao.
 *
 * @author LoveLonelyTime
 */
public interface PUserDAO {
    /**
     * Create a puser.
     *
     * @param puser puser
     * @return puser
     */
    PUserPO save(PUserPO pUser);

    /**
     * Get puser uuid.
     *
     * @param email User's email
     * @return puser
     */
    Optional<PUserPO> findByEmail(String email);
}
