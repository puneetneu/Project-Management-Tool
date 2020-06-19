package com.pun.tan.pmtool.web;


import com.pun.tan.pmtool.domain.ProjectTask;
import com.pun.tan.pmtool.services.ProjectTaskService;
import com.pun.tan.pmtool.services.ValidationError;
import org.springframework.beans.BeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ValidationError validationError;


    @PostMapping("/{backlog_id}")
    public ResponseEntity<?>  addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,
                                             BindingResult result, @PathVariable String backlog_id){

          ResponseEntity<?> errorMap = validationError.ValidationError(result);
          if(errorMap!=null) return errorMap;

          ProjectTask projectTask1= projectTaskService.addProjectTask(backlog_id,projectTask);

          return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }




}
