package com.mfsi.appbuilder.master.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mfsi.appbuilder.master.document.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String>{


	public List<Project> findByUserId(String userId);

}
