package com.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:CustomProps.properties")
public class PropertyConfiguration {

}
