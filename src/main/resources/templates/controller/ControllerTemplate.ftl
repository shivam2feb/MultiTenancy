package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.app.${ApiName}.entity.*;
import com.app.${ApiName}.service.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">
<#assign serviceName = "${entityName}Service">

@RestController<#if controllerMapping??>(value="${controllerMapping}")</#if> 
public class ${EntityName}Controller {
	
	@Autowired
	${EntityName}Service ${serviceName};
	
	@RequestMapping(value="/${entityName}/{id}",method=RequestMethod.GET)
	public ${EntityName} get${EntityName}(@PathVariable ${idType} id) {
		return ${serviceName}.get${EntityName}(id);
	}
	
	@RequestMapping(value="/${entityName}",method=RequestMethod.POST)
	public void add${EntityName}(@RequestBody ${EntityName} ${entityName}) {
		${serviceName}.add${EntityName}(${entityName});
	}
	
	@RequestMapping(value="/${entityName}",method=RequestMethod.PUT)
	public void update${EntityName}(@RequestBody ${EntityName} ${entityName}) {
		${serviceName}.update${EntityName}(${entityName});
	}
	
	@RequestMapping(value="/${entityName}/{id}}",method=RequestMethod.DELETE)
	public void delete${EntityName}(@PathVariable ${idType} id) {
		${serviceName}.delete${EntityName}(id);
	}

}
