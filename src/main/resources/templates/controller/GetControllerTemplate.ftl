package com.app.${ApiName}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.app.${ApiName}.entity.*;
import com.app.${ApiName}.service.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">
<#assign serviceName = "${entityName}Service">
<#assign ApiName = "${ApiName?cap_first}">


@RestController<#if controllerMapping??>(value="${controllerMapping}")</#if> 
public class ${ApiName}Controller {
	
	@Autowired
	${ApiName}Service ${serviceName};
	
	
	@RequestMapping(value="/${ApiUrl}",method=RequestMethod.GET)
	public void get${EntityName}(<#list params as param> @RequestParam ${param.dataType} ${param.columnName} <#if param?is_last>  <#else> "," </#if> </#list>) {
		${serviceName}.get${EntityName}(<#list params as param> ${param.columnName} <#if param?is_last> <#else> "," </#if> </#list>);
	}
}
