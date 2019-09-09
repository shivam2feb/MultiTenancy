package com.app.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.entity.API;
import com.app.repository.APIRepository;

@Configuration
public class SecurityUrls {

	@Autowired
	APIRepository apiRepository;

	@Bean("securityUrlNames")
	public List<API> getSecuredUrls() {
		List<API> apiList = new ArrayList<>();
		apiList = apiRepository.findAll();
		return apiList;
	}

}