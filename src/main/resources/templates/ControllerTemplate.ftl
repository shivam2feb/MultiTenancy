package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.demo.entity.*;
import com.demo.service.TemplateService;

<#assign entityName = "${EntityName?uncap_first}">

@RestController<#if controllerMapping??>(value="${controllerMapping}")</#if>
public class ${EntityName}Controller {
	
	@Autowired
	${EntityName}Service ${entityName}Service;
	
	@RequestMapping(value="/${entityName}/{id}",method=RequestMethod.GET)
	public ${EntityName} get${EntityName}(@PathVariable ${idType} id) {
		return templateService.get${EntityName}(id);
	}
	
	@RequestMapping(value="/${entityName}",method=RequestMethod.POST)
	public void add${EntityName}(@RequestBody ${EntityName} ${entityName}) {
		templateService.add${EntityName}(${entityName});
	}
	
	@RequestMapping(value="/${entityName}",method=RequestMethod.PUT)
	public void update${EntityName}(@RequestBody ${EntityName} ${entityName}) {
		templateService.update${EntityName}(${entityName});
	}
	
	@RequestMapping(value="/${entityName}/{id}}",method=RequestMethod.DELETE)
	public void delete${EntityName}(@PathVariable ${idType} id) {
		templateServcie.delete${EntityName}(id);
	}

}
