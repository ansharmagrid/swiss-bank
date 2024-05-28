package com.swiss.bank.user.service.util;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtTokenUtil {

	private static final String SECRET_HASHING_KEY = "S3CR3T_K3Y";
	private static final String ISSUER_NAME = "admin@swiss-bank.com";
	private static final long EXPIRATION_TIME_MILLIS = 3600 * 1000;

	private Claims getClaimsFromAuthToken(String authToken) {
		return Jwts
				.parser()
				.setSigningKey(SECRET_HASHING_KEY)
				.parseClaimsJws(authToken)
				.getBody();
	}

	public boolean validateToken(String authToken) {
		Claims claims = getClaimsFromAuthToken(authToken);
		return claims
				.getIssuer()
				.equals(ISSUER_NAME) && 
			claims
				.getIssuedAt()
				.after(new Date(System.currentTimeMillis() - EXPIRATION_TIME_MILLIS)) && 
			claims
				.getExpiration()
				.after(new Date());
	}

	public String generateAuthToken(String username) {
		log.atInfo().log("Generating auth token for username: {}", username);
		return Jwts.builder()
				.setSubject(username)
				.setIssuer(ISSUER_NAME)
				.addClaims(Map.of("username", username))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
				.signWith(SignatureAlgorithm.HS512, SECRET_HASHING_KEY)
				.compact();
	}
}
