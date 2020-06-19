package com.pun.tan.pmtool.services;

import com.pun.tan.pmtool.domain.Backlog;
import com.pun.tan.pmtool.domain.ProjectTask;
import com.pun.tan.pmtool.repositories.BacklogRepository;
import com.pun.tan.pmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectidentifier, ProjectTask projectTask){

        Backlog backlog=  backlogRepository.findByProjectIdentifier(projectidentifier);
        projectTask.setBacklog(backlog);
        Integer BacklogSequence=backlog.getPTsequence();
        BacklogSequence++;
        backlog.setPTsequence(BacklogSequence);
        projectTask.setProjectSequence(projectidentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectidentifier);

        if(projectTask.getPriority()==null || projectTask.getPriority()==0){
            projectTask.setPriority(3);
        }

        if(projectTask.getStatus()==null || projectTask.getStatus()==""){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    public  Iterable<ProjectTask> findBackLogById (String id){

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}