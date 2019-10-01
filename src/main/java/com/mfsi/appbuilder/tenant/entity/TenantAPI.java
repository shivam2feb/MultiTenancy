package com.mfsi.appbuilder.tenant.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="api")
public class TenantAPI {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String apiName;
	private String apiType;
	private String apiUrl;
	private boolean secured;
	
	public TenantAPI(){}
	/**
	 * @return the isSecured
	 */
	public boolean isSecured() {
		return secured;
	}
	/**
	 * @param isSecured the isSecured to set
	 */
	public void setSecured(boolean isSecured) {
		this.secured = isSecured;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
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
	public TenantAPI(String apiName, String apiType, String apiUrl, boolean secured) {
		super();
		this.apiName = apiName;
		this.apiType = apiType;
		this.apiUrl = apiUrl;
		this.secured = secured;
	}
	
	
	
}
