package com.app.${ApiName}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.app.entity.*;
import com.app.repository.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class ${ApiName}ServiceImpl implements ${ApiName}Service{
	
	@Autowired 
	${ApiName}Repository repository;
	
	public List<${EntityName}> get${EntityName}(<#list params as param> ${param.dataType} ${param.columnName} <#if param?is_last>  <#else> "," </#if></#list>) {
		repository.findBy${MethodName}(<#list params as param> ${param.columnName} <#if param?is_last>  <#else> "," </#if></#list>);
		
	}
}
