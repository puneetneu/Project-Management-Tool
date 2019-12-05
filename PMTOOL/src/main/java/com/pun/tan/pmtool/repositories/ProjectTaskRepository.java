package com.pun.tan.pmtool.repositories;

import com.pun.tan.pmtool.domain.Backlog;
import com.pun.tan.pmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {
}
