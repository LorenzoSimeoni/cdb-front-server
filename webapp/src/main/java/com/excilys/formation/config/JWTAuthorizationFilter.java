package com.excilys.formation.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With,"
						+ "Content-Type, Access-Control-Allow-Request-Method, Access-Control-Request-Headers,"
						+ "authorization");
		response.addHeader("Access-Control-Expose-Headers",
				"Access-Control-Allow-Origin," + "Access-Control-Allow-Credentials, authorization");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, HEAD, OPTIONS, TRACE, PATCH");
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			String jwt = request.getHeader(SecurityConstant.HEAD_STRING);
			if (jwt == null || !jwt.startsWith(SecurityConstant.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			Claims claims = Jwts.parser().setSigningKey(SecurityConstant.SECRET)
					.parseClaimsJws(jwt.replace(SecurityConstant.TOKEN_PREFIX, "")).getBody();

			String username = claims.getSubject();
			ArrayList<Map<String, String>> role = (ArrayList<Map<String, String>>) claims.get("role");
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			role.forEach(r -> authorities.add(new SimpleGrantedAuthority(r.get("authority"))));

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
					null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			filterChain.doFilter(request, response);
		}
	}
}
