package com.mfsi.appbuilder.multitenant.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;



public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	private static final String DEFAULT_TENANT_ID = "tenant_1";

	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenant = TenantContextHolder.getTenantId();
		return (tenant!=null) ? tenant : DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
