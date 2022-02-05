package com.duplexlearn.dao;

import com.duplexlearn.model.po.ProjectPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectDAO extends JpaRepository<ProjectPO,Long> {
    Optional<ProjectPO> findBySlug(String projectSlug);
}
