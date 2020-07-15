package com.pun.tan.pmtool.services;

import com.pun.tan.pmtool.domain.Backlog;
import com.pun.tan.pmtool.domain.Project;
import com.pun.tan.pmtool.domain.ProjectTask;
import com.pun.tan.pmtool.exceptions.ProjectNotFoundException;
import com.pun.tan.pmtool.repositories.BacklogRepository;
import com.pun.tan.pmtool.repositories.ProjectRepository;
import com.pun.tan.pmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {


    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectidentifier, ProjectTask projectTask){

        try{
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
        }catch(Exception e){
            throw new ProjectNotFoundException("Project Not Found");
        }
    }

    public  Iterable<ProjectTask> findBackLogById (String id){

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project==null)
            throw new ProjectNotFoundException("project with id :'" + id  + "' does not exist");

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }


    public ProjectTask findByPTByProjectSequence(String backlog_id, String pt_id){

        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog==null)
            throw new ProjectNotFoundException("project with id : " + backlog_id + " does not exist");

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask==null)
            throw new ProjectNotFoundException("project task with id : " + pt_id + " does not exist");

        if(!projectTask.getProjectIdentifier().equals(backlog_id))
            throw new ProjectNotFoundException("project task "+ pt_id + " does not exist in project" + backlog_id );


        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){

        ProjectTask projectTask = findByPTByProjectSequence(backlog_id, pt_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id){

        ProjectTask projectTask = findByPTByProjectSequence(backlog_id, pt_id);

        projectTaskRepository.delete(projectTask);

    }
}
