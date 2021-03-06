package com.mfsi.appbuilder.master.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mfsi.appbuilder.master.document.API;


@Repository
public interface APIRepository extends MongoRepository<API, String>{
	
	public List<API> findByProjectId(String projectId);
	public API findByApiUrl(String apiUrl);
	public void deleteByProjectId(String projectId);
	
	
}

