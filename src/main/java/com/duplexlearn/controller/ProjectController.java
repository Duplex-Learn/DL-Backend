package com.duplexlearn.controller;

import com.duplexlearn.model.dto.ProjectDTO;
import com.duplexlearn.model.dto.ProjectsDTO;
import com.duplexlearn.model.vo.ProjectCreateVO;
import com.duplexlearn.model.vo.ProjectVO;
import com.duplexlearn.model.vo.ProjectsVO;
import com.duplexlearn.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/project")
    public ProjectVO createProject(@RequestBody @Valid ProjectCreateVO projectVerifyVO)
    {
        ProjectDTO projectDTO = projectService.createProject(projectVerifyVO.getGit());
        ProjectVO projectVO = new ProjectVO();
        projectVO.setNickname(projectDTO.getNickname());
        projectVO.setUid(projectDTO.getUid());
        projectVO.setId(projectDTO.getId());
        projectVO.setUrl(projectDTO.getUrl());
        return projectVO;
    }

    @GetMapping("/project/{slug}/meta")
    public String getProjectMeta(@PathVariable("slug") String projectSlug)
    {
        return projectService.getProjectMeta(projectSlug);
    }

    @GetMapping("/project/{slug}/")
    public ProjectVO getProject(@PathVariable("slug") String projectSlug)
    {
        ProjectDTO projectDTO = projectService.getProject(projectSlug);
        ProjectVO projectVO = new ProjectVO();
        projectVO.setNickname(projectDTO.getNickname());
        projectVO.setUid(projectDTO.getUid());
        projectVO.setId(projectDTO.getId());
        projectVO.setUrl(projectDTO.getUrl());
        projectVO.setSlug(projectDTO.getSlug());
        return projectVO;
    }

    @GetMapping("/projects/{page}")
    public ProjectsVO getProjects(@PathVariable("page") Integer page)
    {
        ProjectsDTO projectsDTO = projectService.getProjects(page);
        ProjectsVO projectsVO = new ProjectsVO();
        projectsVO.setTotalPages(projectsDTO.getTotalPages());
        projectsVO.setProjectVOS(projectsDTO.getProjectDTOS().stream().map((projectDTO)->{
            ProjectVO projectVO = new ProjectVO();
            projectVO.setId(projectDTO.getId());
            projectVO.setUrl(projectDTO.getUrl());
            projectVO.setUid(projectDTO.getUid());
            projectVO.setSlug(projectDTO.getSlug());
            projectVO.setNickname(projectDTO.getNickname());
            return projectVO;
        }).collect(Collectors.toList()));

        return projectsVO;
    }

    @GetMapping("/project/{p_slug}/classes/{c_slug}")
    public String getClassMeta(@PathVariable("p_slug") String projectSlug,@PathVariable("c_slug") String classSlug)
    {
        return projectService.getClassMeta(projectSlug,classSlug);
    }

    @GetMapping("/project/{p_slug}/classes/{c_slug}/steps/{s_slug}")
    public String getClassContent(@PathVariable("p_slug") String projectSlug,
                                  @PathVariable("c_slug") String classSlug,
                                  @PathVariable("s_slug") String stepSlug)
    {
        return projectService.getClassContent(projectSlug,classSlug,stepSlug);
    }
}
