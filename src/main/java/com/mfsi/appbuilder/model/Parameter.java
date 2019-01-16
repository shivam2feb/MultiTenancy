package com.mfsi.appbuilder.model;

public class Parameter {

	private String dataType;
	private String name;
	private String columnName;
	private Boolean isId=Boolean.FALSE;
	
	
	public Parameter() {
		
	}
	
	public Parameter(String dataType, String name, String columnName, Boolean isId) {
		super();
		this.dataType = dataType;
		this.name = name;
		this.columnName = columnName;
		this.isId = isId;
	}
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Boolean getIsId() {
		return isId;
	}
	public void setIsId(Boolean isId) {
		this.isId = isId;
	}
	
	
	
	
}
