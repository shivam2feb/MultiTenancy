package com.app.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.entity.Matcher;
import com.app.repository.MatcherRepository;

@Configuration
public class SecurityUrls {

	@Autowired
	MatcherRepository matcherRepository;

	@Bean("securityUrlNames")
	public List<Matcher> getSecuredUrls() {
		List<Matcher> urlsList = new ArrayList<Matcher>();
		urlsList = matcherRepository.findAll();
		return urlsList;
	}

}