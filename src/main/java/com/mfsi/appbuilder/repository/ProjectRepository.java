package com.mfsi.appbuilder.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mfsi.appbuilder.document.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{

	
	public List<Project> findByUserUserName(String userName);

	
	public Project findProjectById(String projectId);

	
}
