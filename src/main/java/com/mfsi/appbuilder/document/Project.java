package com.mfsi.appbuilder.document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Project {
	
	@Id
	private String id;
	
	private String projectName;
	private List<Map<String,String>> apiInfo=new ArrayList<>();
	private LocalDateTime lastUpdatedOn;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Map<String, String>> getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(List<Map<String, String>> apiInfo) {
		this.apiInfo = apiInfo;
	}

	public LocalDateTime getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	
	public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	
}