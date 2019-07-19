package com.app.${ApiName}.repository;

import org.springframework.data.repository.CrudRepository;
import com.app.${ApiName}.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
<#assign ApiName = "${ApiName?cap_first}">

public interface ${ApiName}Repository extends CrudRepository<${EntityName},${idType}>{

}
