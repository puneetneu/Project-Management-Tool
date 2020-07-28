package com.pun.tan.pmtool.web;


import com.pun.tan.pmtool.domain.Project;
import com.pun.tan.pmtool.services.ValidationError;
import com.pun.tan.pmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ValidationError errorResponse;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal) {


        ResponseEntity<?> errorMap = errorResponse.ValidationError(result);
        if(errorMap!=null) return errorMap;

        Project project1=projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {

        Project project = projectService.findByProjectIdentifier(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);

    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects(){
        return projectService.findAllProject();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<String>("Project with Id: " + projectId + " deleted successfully", HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project,BindingResult result,@PathVariable Long id,Principal principal){
//
//        ResponseEntity<?> errorMap = errorResponse.ValidationError(result);
//        if(errorMap!=null) return errorMap;
//
//        project.setId(id);
//        Project project1=projectService.saveOrUpdateProject(project, pr);
//        return new ResponseEntity<Project>(project1,HttpStatus.OK);
//    }

}
