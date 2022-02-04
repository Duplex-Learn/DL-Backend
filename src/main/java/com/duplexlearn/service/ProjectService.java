package com.duplexlearn.service;

import com.duplexlearn.model.dto.ProjectDTO;
import com.duplexlearn.model.dto.ProjectsDTO;
import com.duplexlearn.model.dto.UserDTO;

public interface ProjectService {
    ProjectDTO createProject(String url);
    String getProjectMeta(Long id);
    ProjectsDTO getProjects(int page);
    UserDTO getProjectAuthor(Long id);
    String getClassMeta(Long id,String slug);
    String getClassContent(Long id,String slug,String s_slug);
}
