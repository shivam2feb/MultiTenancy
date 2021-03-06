package com.app.${ApiName}.service;

import com.app.${ApiName}.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">
<#assign ApiName = "${ApiName?cap_first}">


public interface ${ApiName}Service {

	public List<${EntityName}> get${EntityName}(<#list params as param> ${param.dataType} ${param.columnName} <#if param?is_last>  <#else> "," </#if></#list> );
}