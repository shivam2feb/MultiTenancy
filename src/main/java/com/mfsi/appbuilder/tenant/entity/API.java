package com.mfsi.appbuilder.tenant.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.mfsi.appbuilder.model.ApiJsonTemplate;

@Entity
public class API {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String apiName;
	private String apiType;
	
	//private List<ApiJsonTemplate> jsonString;
	private String projectName;
	private String projectId;
	private String apiUrl;
	private String mainEntityName;
	private String mainEntityIdType;
	//private List<ApiJsonTemplate> getParams;
	private boolean isSecured;
	
	
	/**
	 * @return the isSecured
	 */
	public boolean isSecured() {
		return isSecured;
	}
	/**
	 * @param isSecured the isSecured to set
	 */
	public void setSecured(boolean isSecured) {
		this.isSecured = isSecured;
	}

	private String reJson;
	
	
	public String getReJson() {
		return reJson;
	}
	public void setReJson(String reJson) {
		this.reJson = reJson;

	}
	/*
	public List<ApiJsonTemplate> getGetParams() {
		return getParams;
	}
	public void setGetParams(List<ApiJsonTemplate> getParams) {
		this.getParams = getParams;
	}
	*/
	public int getId() {
		return id;
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
	/*
	public List<ApiJsonTemplate> getJsonString() {
		return jsonString;
	}
	public void setJsonString(List<ApiJsonTemplate> jsonString) {
		this.jsonString = jsonString;
	}
	*/
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
