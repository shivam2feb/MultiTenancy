package com.mfsi.appbuilder.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mfsi.appbuilder.document.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{

	
	public Project findByProjectName(String projectName);
}
