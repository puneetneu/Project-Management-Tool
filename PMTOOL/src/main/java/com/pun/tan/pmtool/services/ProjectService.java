package com.pun.tan.pmtool.services;

import com.pun.tan.pmtool.domain.Project;
import com.pun.tan.pmtool.exceptions.ProjectIdException;
import com.pun.tan.pmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository pojectRepository;

    public Project saveOrUpdateProject(Project project) {
       try{
           project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
           return pojectRepository.save(project);
       }catch (Exception e){
           throw new ProjectIdException("Project ID -" + project.getProjectIdentifier().toUpperCase() + " Already exist");
       }


    }
}
