package com.pun.tan.pmtool.services;

import com.pun.tan.pmtool.domain.Backlog;
import com.pun.tan.pmtool.domain.Project;
import com.pun.tan.pmtool.domain.User;
import com.pun.tan.pmtool.exceptions.ProjectIdException;
import com.pun.tan.pmtool.exceptions.ProjectNotFoundException;
import com.pun.tan.pmtool.repositories.BacklogRepository;
import com.pun.tan.pmtool.repositories.ProjectRepository;
import com.pun.tan.pmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {


        if(project.getId()!=null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject != null && (!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject==null)
                throw  new ProjectNotFoundException("Project with ID : " + project.getProjectIdentifier() + "does not exist");
        }



        try{

           User user =userRepository.findByUsername(username);
           project.setUser(user);
           project.setProjectLeader(user.getUsername());

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

    public Project findByProjectIdentifier(String projectId, String username){

        Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project Id - "+ projectId+ " does not exist"  );
        }


        if(!project.getProjectLeader().equals(username)){
            throw new ProjectIdException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProject(String username){
        return projectRepository.findAllByProjectLeader(username);
    }


    public void  deleteProjectByIdentifier(String projectId, String username){
        projectRepository.delete(findByProjectIdentifier(projectId,username));

    }

}
