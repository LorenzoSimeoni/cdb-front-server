package com.excilys.formation.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.excilys.formation.model.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
	private final static Logger LOGGER = LogManager.getLogger(JWTAuthentificationFilter.class.getName());
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		User user=null;
		
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		} catch (JsonParseException e) {
			LOGGER.info("Request not being parsed correctly",e);
		} catch (JsonMappingException e) {
			LOGGER.info("Request not being mapped correctly",e);
		} catch (IOException e) {
			LOGGER.info("Can't access to the request",e);
		}
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		org.springframework.security.core.userdetails.User springUser = 
				(org.springframework.security.core.userdetails.User)authResult.getPrincipal();
		
		String jwt = Jwts.builder()
				.setSubject(springUser.getUsername())
				.setExpiration(new Date(System.currentTimeMillis()+SecurityConstant.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET)
				.claim("role", springUser.getAuthorities())
				.compact();
				
		response.addHeader(SecurityConstant.HEAD_STRING, SecurityConstant.TOKEN_PREFIX + jwt);
	}
}
