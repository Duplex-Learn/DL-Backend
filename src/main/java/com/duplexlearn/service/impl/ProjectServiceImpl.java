package com.duplexlearn.service.impl;

import com.duplexlearn.dao.ProjectDAO;
import com.duplexlearn.exception.IllegalProjectException;
import com.duplexlearn.exception.ProjectNotFoundException;
import com.duplexlearn.model.dto.ProjectDTO;
import com.duplexlearn.model.dto.ProjectsDTO;
import com.duplexlearn.model.dto.UserDTO;
import com.duplexlearn.model.po.ProjectPO;
import com.duplexlearn.model.po.UserPO;
import com.duplexlearn.model.vo.ProjectMetaVO;
import com.duplexlearn.service.ProjectService;
import com.duplexlearn.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {
    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;
    private ProjectDAO projectDAO;
    private UserService userService;

    @Autowired
    public ProjectServiceImpl(ObjectMapper objectMapper,RestTemplate restTemplate,ProjectDAO projectDAO,UserService userService) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.projectDAO = projectDAO;
        this.userService = userService;
    }

    private void checkProjectMeta(ProjectMetaVO projectMetaVO)
    {

    }

    @Override
    public ProjectDTO createProject(String url) {
        try {
            URL Url = new URL(new URL(url), "./raw/master/meta.json");
            String str = restTemplate.getForEntity(Url.toURI(), String.class).getBody();
            ProjectMetaVO projectMetaVO = objectMapper.readValue(str, ProjectMetaVO.class);
            checkProjectMeta(projectMetaVO);
        }catch (Exception e)
        {
            throw new IllegalProjectException(url,e);
        }

        UserDTO userDTO = userService.getCurrentUser();
        UserPO userPO = new UserPO();
        userPO.setId(userDTO.getId());

        ProjectPO projectPO = new ProjectPO();
        projectPO.setUrl(url);
        projectPO.setUserPO(userPO);

        projectPO = projectDAO.save(projectPO);

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectPO.getId());
        projectDTO.setUrl(projectPO.getUrl());

        return projectDTO;
    }

    @Override
    public String getProjectMeta(Long id) {
        ProjectPO projectPO = projectDAO.findById(id).orElseThrow(()-> new ProjectNotFoundException(id));
        String content = restTemplate.getForEntity(projectPO.getUrl()+"raw/master/meta.json",String.class).getBody();
        return content;
    }

    @Override
    public ProjectsDTO getProjects(int page)
    {
        ProjectsDTO projectsDTO = new ProjectsDTO();
        Page<ProjectPO> projects = projectDAO.findAll(PageRequest.of(page,10));
        projectsDTO.setProjectDTOS(projects.stream().map((projectPO) ->
        {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setUrl(projectPO.getUrl());
            projectDTO.setId(projectPO.getId());
            projectDTO.setUid(projectPO.getUserPO().getId());
            return projectDTO;
        }).collect(Collectors.toList()));
        projectsDTO.setTotalPages(projects.getTotalPages());
        return projectsDTO;
    }

    @Override
    public UserDTO getProjectAuthor(Long id) {
        ProjectPO projectPO = projectDAO.findById(id).orElseThrow(()-> new ProjectNotFoundException(id));
        UserDTO userDTO = new UserDTO();
        userDTO.setNickname(projectPO.getUserPO().getNickname());
        return userDTO;
    }

    @Override
    public String getClassMeta(Long id, String slug) {
        ProjectPO projectPO = projectDAO.findById(id).orElseThrow(()-> new ProjectNotFoundException(id));
        String content = restTemplate.getForEntity(projectPO.getUrl()+"raw/master/" + slug + "/meta.json",String.class).getBody();
        return content;
    }

    @Override
    public String getClassContent(Long id, String slug, String s_slug) {
        ProjectPO projectPO = projectDAO.findById(id).orElseThrow(()-> new ProjectNotFoundException(id));
        String content = restTemplate.getForEntity(projectPO.getUrl()+"raw/master/" + slug + "/" +s_slug + ".md",String.class).getBody();
        return content;
    }
}
