package com.app.service;

import com.app.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign entityName = "${EntityName?uncap_first}">

public interface ${ApiName}Service {

	public void add${EntityName}(${EntityName} ${entityName});
}