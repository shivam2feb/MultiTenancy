package com.mfsi.appbuilder.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mfsi.appbuilder.document.API;


@Repository
public interface APIRepository extends MongoRepository<API, String>{
	
	public List<API> findByProjectId(String projectId);
	
}

