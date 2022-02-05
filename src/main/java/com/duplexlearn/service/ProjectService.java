package com.duplexlearn.service;

import com.duplexlearn.model.dto.ProjectDTO;
import com.duplexlearn.model.dto.ProjectsDTO;
import com.duplexlearn.model.dto.UserDTO;

public interface ProjectService {
    ProjectDTO createProject(String url);
    String getProjectMeta(String projectSlug);
    ProjectsDTO getProjects(int page);
    ProjectDTO getProject(String projectSlug);
    String getClassMeta(String projectSlug,String classSlug);
    String getClassContent(String projectSlug,String classSlug,String contentSlug);
}
