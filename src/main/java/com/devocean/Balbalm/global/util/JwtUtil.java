package com.devocean.Balbalm.global.util;


import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
public class JwtUtil {
	private final String key;
	public JwtUtil(@Value("${jwt.secret}") String key) {
		this.key = key;
	}

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	}

	public String extractSocialId(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claims.get("social_id", String.class);
	}
}
