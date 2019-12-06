package com.pun.tan.pmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Backlog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTsequence =0;
    private String projectIdentifier;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;


    public Backlog() {
    }

    public Long getId(){
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPTsequence() {
        return PTsequence;
    }

    public void setPTsequence(Integer PTsequence) {
        this.PTsequence = PTsequence;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
