package com.mfsi.appbuilder.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectDTO {

	private String projectName;
	private List<Map<String,String>> apiInfo=new ArrayList<>();
	private LocalDateTime lastUpdatedOn;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
