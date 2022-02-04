package com.duplexlearn.model.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectCreateVO {
    @NotBlank
    @Length(max = 200)
    private String git;
}
