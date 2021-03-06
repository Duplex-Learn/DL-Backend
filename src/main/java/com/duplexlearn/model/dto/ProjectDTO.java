package com.duplexlearn.model.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private Long id;
    private String url;
    private String nickname;
    private String slug;
    private Long uid;
}
