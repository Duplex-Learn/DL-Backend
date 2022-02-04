package com.duplexlearn.controller;

import com.duplexlearn.model.dto.ProjectDTO;
import com.duplexlearn.model.dto.ProjectsDTO;
import com.duplexlearn.model.po.ProjectPO;
import com.duplexlearn.model.vo.AuthorVO;
import com.duplexlearn.model.vo.ProjectCreateVO;
import com.duplexlearn.model.vo.ProjectVO;
import com.duplexlearn.model.vo.ProjectsVO;
import com.duplexlearn.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
        projectVO.setUrl(projectDTO.getUrl());
        projectVO.setId(projectDTO.getId());
        return projectVO;
    }

    @GetMapping("/project/{id}/meta")
    public String getProjectMeta(@PathVariable("id") Long id)
    {
        return projectService.getProjectMeta(id);
    }

    @GetMapping("/project/{id}/author")
    public AuthorVO getProjectAuthor(@PathVariable("id") Long id)
    {
        AuthorVO authorVO = new AuthorVO();
        authorVO.setNickname(projectService.getProjectAuthor(id).getNickname());
        return authorVO;
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
            return projectVO;
        }).collect(Collectors.toList()));

        return projectsVO;
    }

    @GetMapping("/project/{id}/classes/{slug}")
    public String getClassMeta(@PathVariable("id") Long id,@PathVariable("slug") String slug)
    {
        return projectService.getClassMeta(id,slug);
    }

    @GetMapping("/project/{id}/classes/{slug}/steps/{s_slug}")
    public String getClassContent(@PathVariable("id") Long id,
                                  @PathVariable("slug") String slug,
                                  @PathVariable("s_slug") String s_slug)
    {
        return projectService.getClassContent(id,slug,s_slug);
    }
}
