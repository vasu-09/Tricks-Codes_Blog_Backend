package com.tandc.blog.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
private String jwtSecret = "f93c9b55c8d00c302bc7aee3c87b707cb96b0465d64ac3bc85955d4396e1e3de";
	
	private Long jwtExpirationDate = (long) 604800000;
	
	// Generate JWT token
	public String generateToke(Authentication authentication) {
		String username=authentication.getName();
		
		Date curretDate= new Date();
		
		Date expireDate = new Date(curretDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder()
			.setSubject(username)
			.setIssuedAt(new Date())
			.setExpiration(expireDate)
			.signWith(key())
			.compact();
		
		return token;
	}
	
	
	private Key key() {
		return Keys.hmacShaKeyFor(
				Decoders.BASE64.decode(jwtSecret)
				);
				
	}
	
	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody();
				
		String username = claims.getSubject();
		return username;
	
		
	}
	
	public boolean validateToken(String token) {
						Jwts.parserBuilder()
								.setSigningKey(key())
								.build()
								.parse(token);
						
						return true;
		
	}

}
