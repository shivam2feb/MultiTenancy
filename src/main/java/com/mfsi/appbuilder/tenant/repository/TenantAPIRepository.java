package com.mfsi.appbuilder.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mfsi.appbuilder.tenant.entity.TenantAPI;

@Repository
public interface TenantAPIRepository extends JpaRepository<TenantAPI, Integer>{
	
	public TenantAPI findByApiUrl(String url);
}
