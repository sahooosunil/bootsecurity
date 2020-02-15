package com.sks.securityFull.jwt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authManager;
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			UsernamePasswordAuthenticationRequest authRequest = 
					new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authRequest.getUsername(),
					authRequest.getPassword()
					);
			Authentication authenticate = authManager.authenticate(authentication);
			return authenticate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String SecretKey = "securesecuresecuresecuresecuresecuresecuresecure";
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
				.signWith(Keys.hmacShaKeyFor(SecretKey.getBytes()))
				.compact();
		response.addHeader("Authorization", "Bearer "+token );
		
	}
	
	
	
}
