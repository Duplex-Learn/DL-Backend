package com.duplexlearn.model.po;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ProjectPO {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String url;

    @Column(unique = true)
    private String slug;

    @ManyToOne
    private UserPO userPO;
}
