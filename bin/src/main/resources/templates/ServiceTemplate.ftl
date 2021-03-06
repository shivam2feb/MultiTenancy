package com.app.service;

import com.app.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">

public interface ${EntityName}Service {
	
	public ${EntityName} get${EntityName}(${idType} id);
	public void add${EntityName}(${EntityName} ${entityName});
	public void update${EntityName}(${EntityName} ${entityName});
	public void delete${EntityName}(${idType} id);

}