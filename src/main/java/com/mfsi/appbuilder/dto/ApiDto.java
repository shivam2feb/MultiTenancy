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

	private boolean secured;



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiName == null) ? 0 : apiName.hashCode());
		result = prime * result + ((apiType == null) ? 0 : apiType.hashCode());
		result = prime * result + ((apiUrl == null) ? 0 : apiUrl.hashCode());
		result = prime * result + ((getParams == null) ? 0 : getParams.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (secured ? 1231 : 1237);
		result = prime * result + ((jsonString == null) ? 0 : jsonString.hashCode());
		result = prime * result + ((mainEntityIdType == null) ? 0 : mainEntityIdType.hashCode());
		result = prime * result + ((mainEntityName == null) ? 0 : mainEntityName.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiDto other = (ApiDto) obj;
		if (apiName == null) {
			if (other.apiName != null)
				return false;
		} else if (!apiName.equals(other.apiName))
			return false;
		if (apiType == null) {
			if (other.apiType != null)
				return false;
		} else if (!apiType.equals(other.apiType))
			return false;
		if (apiUrl == null) {
			if (other.apiUrl != null)
				return false;
		} else if (!apiUrl.equals(other.apiUrl))
			return false;
		if (getParams == null) {
			if (other.getParams != null)
				return false;
		} else if (!getParams.equals(other.getParams))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (secured != other.secured)
			return false;
		if (jsonString == null) {
			if (other.jsonString != null)
				return false;
		} else if (!jsonString.equals(other.jsonString))
			return false;
		if (mainEntityIdType == null) {
			if (other.mainEntityIdType != null)
				return false;
		} else if (!mainEntityIdType.equals(other.mainEntityIdType))
			return false;
		if (mainEntityName == null) {
			if (other.mainEntityName != null)
				return false;
		} else if (!mainEntityName.equals(other.mainEntityName))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		return true;
	}

	public boolean isSecured() {
		return secured;
	}

	public void setSecured(boolean secured) {
		this.secured = secured;
	}
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
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
