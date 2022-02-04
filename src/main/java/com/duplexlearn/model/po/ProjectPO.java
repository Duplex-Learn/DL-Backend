package com.duplexlearn.model.po;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ProjectPO {
    @Id
    @GeneratedValue
    private Long id;

    // TODO @Column(unique = true)
    private String url;

    @ManyToOne
    private UserPO userPO;

}
