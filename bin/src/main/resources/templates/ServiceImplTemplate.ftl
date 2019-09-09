package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.*;
import com.app.repository.*;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class ${EntityName}ServiceImpl implements ${EntityName}Service{
	<#assign EntityName = "${EntityName?cap_first}">
	<#assign entityName = "${EntityName?uncap_first}">
	<#assign repository ="${entityName}Repository" >
	
	@Autowired 
	${EntityName}Repository ${repository};

	public ${EntityName} get${EntityName}(${idType} id) {
		return ${repository}.findById(id).get();
	}
	
	public void add${EntityName}(${EntityName} ${entityName}) {
		${repository}.save(${entityName});
		
	}
	
	public void update${EntityName}(${EntityName} ${entityName}) {
		${repository}.save(${entityName});
	}
	
	public void delete${EntityName}(${idType} id) {
		${repository}.deleteById(id);
	}
	
}
