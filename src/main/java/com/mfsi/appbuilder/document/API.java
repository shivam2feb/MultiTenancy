package com.mfsi.appbuilder.document;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mfsi.appbuilder.model.ApiJsonTemplate;

@Document
public class API {
	private String id;
	private String apiName;
	private String apiType;
	private List<ApiJsonTemplate> jsonString;
	private String projectName;
	private String projectId;
	private String apiUrl;
	private String mainEntityName;
	private String mainEntityIdType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiType() {
		return apiType;
	}
	public void setApiType(String apiType) {
		this.apiType = apiType;
	}
	public List<ApiJsonTemplate> getJsonString() {
		return jsonString;
	}
	public void setJsonString(List<ApiJsonTemplate> jsonString) {
		this.jsonString = jsonString;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getMainEntityName() {
		return mainEntityName;
	}
	public void setMainEntityName(String mainEntityName) {
		this.mainEntityName = mainEntityName;
	}
	public String getMainEntityIdType() {
		return mainEntityIdType;
	}
	public void setMainEntityIdType(String mainEntityIdType) {
		this.mainEntityIdType = mainEntityIdType;
	}
}
