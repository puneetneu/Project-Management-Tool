package com.pun.tan.pmtool.repositories;

import com.pun.tan.pmtool.domain.Backlog;
import com.pun.tan.pmtool.domain.Project;
import com.pun.tan.pmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {

     List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

     ProjectTask findByProjectSequence(String sequence);
}
