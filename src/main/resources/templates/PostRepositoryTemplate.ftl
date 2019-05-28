package com.app.repository;

import org.springframework.data.repository.CrudRepository;
import com.app.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
public interface ${ApiName}Repository extends CrudRepository<${EntityName},${idType}>{

}
