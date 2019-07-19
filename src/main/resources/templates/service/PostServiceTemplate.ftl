package com.app.${ApiName}.service;

import com.app.${ApiName}.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">
<#assign ApiName = "${ApiName?cap_first}">


public interface ${ApiName}Service {

	public void add${EntityName}(${EntityName} ${entityName});
}