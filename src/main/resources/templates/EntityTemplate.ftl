package com.app.entity;

import java.util.*;
import javax.persistence.*;

<#assign EntityName = "${EntityName?cap_first}">
@Entity<#if tableName??>(name="${tableName}")</#if>
public class ${EntityName} {
	
	<#list params as param>
	<#if param.isId>
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	<#if param.columnName??>@Column(name="${param.columnName}")</#if>
	<#else>
	@Column<#if param.columnName??>(name="${param.columnName}")</#if>
	</#if>
	private ${param.dataType} ${param.name};
	
	public ${param.dataType} get${param.name?cap_first}(){
		return ${param.name};
	}
	
	public void set${param.name?cap_first}(${param.dataType} ${param.name}){
		this.${param.name}=${param.name};
	}
	
	</#list>
}
