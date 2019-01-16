package com.mfsi.appbuilder.dto;

public class APIDTO {

	private String id;
	private String apiName;
	private String apiType;
	private String jsonString;

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
		this.apiType=apiType;
	}

	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
}
