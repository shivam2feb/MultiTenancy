package com.app.${ApiName}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.app.${ApiName}.entity.*;
import com.app.${ApiName}.repository.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">
<#assign ApiName = "${ApiName?cap_first}">


@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class ${ApiName}ServiceImpl implements ${ApiName}Service{
	
	@Autowired 
	${ApiName}Repository repository;
	
	public void update${EntityName}(${EntityName} ${entityName}) {
		repository.save(${entityName});
		
	}
}
