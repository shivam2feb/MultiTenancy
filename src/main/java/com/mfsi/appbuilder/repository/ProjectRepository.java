package com.mfsi.appbuilder.repository;

import com.mfsi.appbuilder.document.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{


	public List<Project> findByUserId(String userId);


	public Project findProjectBy_id(String projectId);

}
