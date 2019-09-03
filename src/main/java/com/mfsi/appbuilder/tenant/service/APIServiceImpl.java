package com.mfsi.appbuilder.tenant.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.tenant.entity.API;
import com.mfsi.appbuilder.tenant.repository.TenantAPIRepository;

@Service
public class APIServiceImpl implements APIService{

	@Autowired
	TenantAPIRepository apiRepo;
	
	@Override
	public void saveAPI(ApiDto apiDto) {
		API api = new API();
		BeanUtils.copyProperties(apiDto, api);
		apiRepo.save(api);
	}

}
