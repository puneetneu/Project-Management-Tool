package com.pun.tan.pmtool.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Backlog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTsequence =0;
    private String projectIdentifier;


    public Backlog() {
    }

    public Long getId(){
        return id;
    }

}
