package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(value = {PersistenceConfig.class})
@ComponentScan(basePackages = { "com.excilys.formation.service",
		"com.excilys.formation.validator" })
public class ServiceConfig {

}
