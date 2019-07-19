package com.app.repository;

import org.springframework.data.repository.CrudRepository;
import com.app.${ApiName}.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign ApiName = "${ApiName?cap_first}">

public interface ${EntityName}Repository extends CrudRepository<${EntityName},${idType}>{

}
