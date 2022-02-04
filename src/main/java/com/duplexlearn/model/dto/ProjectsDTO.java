package com.duplexlearn.model.dto;

import com.duplexlearn.model.vo.ProjectVO;
import lombok.Data;

import java.util.List;

@Data
public class ProjectsDTO {
    private Integer totalPages;
    private List<ProjectDTO> projectDTOS;
}