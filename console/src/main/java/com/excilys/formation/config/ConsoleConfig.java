package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {ServiceConfig.class,BindingConfig.class,CoreConfig.class,PersistenceConfig.class}) 
@ComponentScan(basePackages = { "com.excilys.formation.cli" })
public class ConsoleConfig {
	
}
