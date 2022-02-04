package com.duplexlearn.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectsVO {
    private Integer totalPages;

    @JsonProperty("projects")
    private List<ProjectVO> projectVOS;
}
