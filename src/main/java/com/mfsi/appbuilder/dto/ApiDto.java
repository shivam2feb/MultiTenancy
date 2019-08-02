package com.mfsi.appbuilder.dto;

import java.util.List;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mfsi.appbuilder.model.ApiJsonTemplate;

public class ApiDto {

	private String id;
	private String apiName;
	private String apiType;
	private List<ApiJsonTemplate> jsonString;
	private String projectName;
	private String projectId;
	private String apiUrl;
	private String mainEntityName;
	private String mainEntityIdType;
	private List<ApiJsonTemplate> getParams;
	private String reJson;
	
	

	
	public String getReJson() {
		return reJson;
	}

	public void setReJson(String reJson) {
		this.reJson = reJson;
	}

	public List<ApiJsonTemplate> getGetParams() {
		return getParams;
	}

	public void setGetParams(List<ApiJsonTemplate> getParams) {
		this.getParams = getParams;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getMainEntityIdType() {
		return mainEntityIdType;
	}

	public void setMainEntityIdType(String mainEntityIdType) {
		this.mainEntityIdType = mainEntityIdType;
	}

	public String getMainEntityName() {
		return mainEntityName;
	}

	public void setMainEntityName(String mainEntityName) {
		this.mainEntityName = mainEntityName;
	}

	public List<ApiJsonTemplate> getJsonString() {
		return jsonString;
	}

	public void setJsonString(List<ApiJsonTemplate> jsonString) {
		this.jsonString = jsonString;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectID) {
		this.projectName = projectID;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiType() {
		return this.apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	

}
