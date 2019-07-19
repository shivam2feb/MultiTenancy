package com.app.${ApiName}.repository;

import org.springframework.data.repository.CrudRepository;
import com.app.${ApiName}.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign ApiName = "${ApiName?cap_first}">

public interface ${ApiName}Repository extends CrudRepository<${EntityName},${idType}>{
	public List<${EntityName}> findBy${MethodName}(<#list params as param> ${param.dataType} ${param.columnName} <#if param?is_last>  <#else> "," </#if></#list>);
}
