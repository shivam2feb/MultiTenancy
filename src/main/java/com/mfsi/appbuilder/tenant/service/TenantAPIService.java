package com.mfsi.appbuilder.tenant.service;

import java.util.List;

import com.mfsi.appbuilder.dto.ApiDto;
import com.mfsi.appbuilder.master.document.API;
import com.mfsi.appbuilder.tenant.entity.TenantAPI;

public interface TenantAPIService {

	public void saveAPIs(List<API> api);
	public void saveAPI(ApiDto apiDTO);
	public TenantAPI findAPIbyURL(String url);
	public void updateAPI(ApiDto apiDto,TenantAPI preAPI);
	public void deleteByUrl(String url);
}
