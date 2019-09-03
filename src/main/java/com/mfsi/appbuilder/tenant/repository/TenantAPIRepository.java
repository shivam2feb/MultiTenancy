package com.mfsi.appbuilder.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfsi.appbuilder.tenant.entity.API;

@Repository
public interface TenantAPIRepository extends JpaRepository<API, Integer>{

}
