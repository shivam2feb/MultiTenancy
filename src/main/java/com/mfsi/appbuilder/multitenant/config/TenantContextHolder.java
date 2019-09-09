package com.mfsi.appbuilder.multitenant.config;

public class TenantContextHolder {
	
	private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
	
	public static void setTenantId(String tenant) {
		CONTEXT.set(tenant);
	}
	
	public static String getTenantId() {
		return CONTEXT.get();
	}
	
	public static void remove() {
		CONTEXT.remove();
	}

}
