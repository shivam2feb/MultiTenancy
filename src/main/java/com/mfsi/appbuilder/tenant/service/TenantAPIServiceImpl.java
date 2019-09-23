package com.mfsi.appbuilder.tenant.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.master.document.API;
import com.mfsi.appbuilder.tenant.entity.TenantAPI;
import com.mfsi.appbuilder.tenant.repository.TenantAPIRepository;

@Service
public class TenantAPIServiceImpl implements TenantAPIService{

	@Autowired
	TenantAPIRepository apiRepo;

	@Override
	public void saveAPIs(List<API> apis) {
		
		List<TenantAPI> apisToSave = apis.stream().collect(Collectors.mapping(api -> new TenantAPI(api.getApiName(), api.getApiType(),
				api.getApiUrl(), api.isSecured()), Collectors.toList()));
		
		apiRepo.saveAll(apisToSave);
	}

	public void saveAPI(ApiDto apiDTO) {
		
		TenantAPI tenantAPI = new TenantAPI();
		BeanUtils.copyProperties(apiDTO, tenantAPI);
		apiRepo.save(tenantAPI);
	}

	@Override
	public TenantAPI findAPIbyURL(String url) {
		return apiRepo.findByApiUrl(url);
	}

	@Override
	public void updateAPI(ApiDto apiDto, TenantAPI preAPI) {
		TenantAPI tenantAPI= new TenantAPI();
		tenantAPI.setId(preAPI.getId());
		BeanUtils.copyProperties(apiDto, tenantAPI, "id");
		apiRepo.save(tenantAPI);
		
	}
	
	@Override
	public void deleteByUrl(String url) {
		apiRepo.removeByApiUrl(url);
	}
}
