package com.excilys.formation.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.excilys.formation.service.UserService;

@Configuration
@EnableWebSecurity
@Import(value = { BindingConfig.class, ServiceConfig.class })
@ComponentScan(basePackages = { "com.excilys.formation.controller" })
public class WebAppConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userDetailsService;
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
	    auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	public void configure(WebSecurity web)
	  throws Exception {
	   web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and()
		.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/").permitAll()
			.antMatchers(HttpMethod.POST,"/registration").permitAll()
			.antMatchers(HttpMethod.POST, "/Computer-rest/create-rest", "/Computer").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/Computer-rest/update-rest/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/Computer-rest/**").hasRole("ADMIN")
			.antMatchers("/Computer/update/**", "/Computer/add").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.and()
		.httpBasic()
		.and()
		.logout().permitAll();
	}

}