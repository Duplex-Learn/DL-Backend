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
import com.duplexlearn.model.vo.ProjectVO;
import com.duplexlearn.service.ProjectService;
import com.duplexlearn.service.UserService;
import com.duplexlearn.util.RSAUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {
    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;
    private ProjectDAO projectDAO;
    private UserService userService;
    private RSAUtil rsaUtil;

    private PublicKey publicKey;

    @Value("${dl.secret}")
    public void setPrivateKey(String secret) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.publicKey = rsaUtil.getPublicKey(secret);
    }

    @Autowired
    public ProjectServiceImpl(ObjectMapper objectMapper,RestTemplate restTemplate,ProjectDAO projectDAO,UserService userService,RSAUtil rsaUtil) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.projectDAO = projectDAO;
        this.userService = userService;
        this.rsaUtil = rsaUtil;
    }

    private void checkProjectMeta(String url,ProjectMetaVO projectMetaVO) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if(!rsaUtil.verify(url,projectMetaVO.getSignature(),publicKey))
        {
            throw new SignatureException();
        }
    }

    @Override
    public ProjectDTO createProject(String url) {
        ProjectMetaVO projectMetaVO = null;
        try {
            URL Url = new URL(new URL(url), "./raw/master/meta.json");
            String str = restTemplate.getForEntity(Url.toURI(), String.class).getBody();
            projectMetaVO = objectMapper.readValue(str, ProjectMetaVO.class);
            checkProjectMeta(url,projectMetaVO);
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
        projectPO.setSlug(projectMetaVO.getSlug());

        projectPO = projectDAO.save(projectPO);

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(projectPO.getId());
        projectDTO.setUrl(projectPO.getUrl());
        projectDTO.setNickname(userDTO.getNickname());
        projectDTO.setUid(userDTO.getId());
        return projectDTO;
    }

    @Override
    public String getProjectMeta(String projectSlug) {
        ProjectPO projectPO = projectDAO.findBySlug(projectSlug).orElseThrow(()-> new ProjectNotFoundException(projectSlug));
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
            projectDTO.setSlug(projectPO.getSlug());
            projectDTO.setNickname(projectPO.getUserPO().getNickname());
            return projectDTO;
        }).collect(Collectors.toList()));
        projectsDTO.setTotalPages(projects.getTotalPages());
        return projectsDTO;
    }

    @Override
    public ProjectDTO getProject(String projectSlug) {
        ProjectPO projectPO = projectDAO.findBySlug(projectSlug).orElseThrow(()-> new ProjectNotFoundException(projectSlug));

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setNickname(projectPO.getUserPO().getNickname());
        projectDTO.setId(projectPO.getId());
        projectDTO.setUrl(projectPO.getUrl());
        projectDTO.setUid(projectPO.getUserPO().getId());
        projectDTO.setSlug(projectPO.getSlug());
        return projectDTO;
    }

    @Override
    public String getClassMeta(String projectSlug,String classSlug) {
        ProjectPO projectPO = projectDAO.findBySlug(projectSlug).orElseThrow(()-> new ProjectNotFoundException(projectSlug));
        String content = restTemplate.getForEntity(projectPO.getUrl()+"raw/master/" + classSlug + "/meta.json",String.class).getBody();
        return content;
    }

    @Override
    public String getClassContent(String projectSlug,String classSlug,String contentSlug) {
        ProjectPO projectPO = projectDAO.findBySlug(projectSlug).orElseThrow(()-> new ProjectNotFoundException(projectSlug));
        String content = restTemplate.getForEntity(projectPO.getUrl()+"raw/master/" + classSlug + "/" + contentSlug + ".md",String.class).getBody();
        return content;
    }
}
