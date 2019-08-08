package com.mfsi.appbuilder.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectDTO {

	private String _id;
	private String projectName;
	private List<Map<String,String>> apiInfo=new ArrayList<>();
	private String lastUpdatedOn;
	private String userId;
	private DBDetailsDTO dbDetailsDTO;
	private Boolean wantSecurity = true;

	

	/**
	 * @return the wantSecurity
	 */
	public Boolean getWantSecurity() {
		return wantSecurity;
	}

	/**
	 * @param wantSecurity the wantSecurity to set
	 */
	public void setWantSecurity(Boolean wantSecurity) {
		this.wantSecurity = wantSecurity;
	}


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

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

	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public DBDetailsDTO getDbDetailsDTO() {
		if (dbDetailsDTO == null)
			dbDetailsDTO = new DBDetailsDTO();
		return dbDetailsDTO;
	}

	public void setDbDetailsDTO(DBDetailsDTO dbDetailsDTO) {
		this.dbDetailsDTO = dbDetailsDTO;
	}
}
