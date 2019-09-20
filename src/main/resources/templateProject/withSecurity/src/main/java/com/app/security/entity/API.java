package com.app.security.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class API {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String apiName;
	private String apiType;
	private String apiUrl;
	private boolean secured;
	
	
	/**
	 * @return the secured
	 */
	public boolean isSecured() {
		return secured;
	}
	/**
	 * @param isSecured the isSecured to set
	 */
	public void setSecured(boolean secured) {
		this.secured = secured;
	}

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
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	
}
