package com.app.repository;

import org.springframework.data.repository.CrudRepository;
import com.app.entity.*;

<#assign EntityName = "${EntityName?cap_first}">
public interface ${EntityName}Repository extends CrudRepository<${EntityName},${idType}>{

}
