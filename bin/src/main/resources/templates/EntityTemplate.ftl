package com.app.${ApiName}.entity;

import java.util.*;
import javax.persistence.*;
import com.app.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
@Entity<#if tableName??>(name="${tableName}")</#if>
public class ${EntityName} {
	
	<#list params as param>
	<#if param.isPrimaryKey>
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	<#if param.columnName??>@Column(name="${param.columnName}")</#if>
	<#else>
	@Column<#if param.columnName??>(name="${param.columnName}")</#if>
	</#if>
	private ${param.dataType} ${param.columnName};
	
	public ${param.dataType} get${param.columnName?cap_first}(){
		return ${param.columnName};
	}
	
	public void set${param.columnName?cap_first}(${param.dataType} ${param.columnName}){
		this.${param.columnName}=${param.columnName};
	}
	
	</#list>
}
