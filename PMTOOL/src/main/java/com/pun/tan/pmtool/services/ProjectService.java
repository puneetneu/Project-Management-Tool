package com.pun.tan.pmtool.services;

import com.pun.tan.pmtool.domain.Backlog;
import com.pun.tan.pmtool.domain.Project;
import com.pun.tan.pmtool.exceptions.ProjectIdException;
import com.pun.tan.pmtool.repositories.BacklogRepository;
import com.pun.tan.pmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
       try{
           project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

           if(project.getId()==null){
               Backlog backlog = new Backlog();
               backlog.setProject(project);
               backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
               project.setBacklog(backlog);
           }

           if(project.getId()!=null){
               project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
           }



           return projectRepository.save(project);
       }catch (Exception e){
           throw new ProjectIdException("Project ID -" + project.getProjectIdentifier().toUpperCase() + " Already exist");
       }


    }

    public Project findByProjectIdentifier(String projectId){

        Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project Id - "+ projectId+ " does not exist"  );
        }

        return project;
    }

    public Iterable<Project> findAllProject(){
        return projectRepository.findAll();
    }


    public void  deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project Id - "+ projectId+ " does not exist"  );
        }

        projectRepository.delete(project);

    }

}
