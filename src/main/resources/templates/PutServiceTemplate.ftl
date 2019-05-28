package com.app.${ApiName}.service;

import com.app.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">

public interface ${ApiName}Service {

	public void update${EntityName}(${EntityName} ${entityName});
}