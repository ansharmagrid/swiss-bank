package com.swiss.bank.user.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.swiss.bank.user.service.repositories.UserRepository;
import com.swiss.bank.user.service.util.DataUtil;
import com.swiss.bank.user.service.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtRequestFilter implements WebFilter {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		HttpCookie authCookie = exchange.getRequest().getCookies().getFirst("auth_token");
		HttpCookie usernameCookie = exchange.getRequest().getCookies().getFirst("username");
		if (authCookie == null || usernameCookie == null) {
			log.atInfo().log("cookie not found for authentication or username");
			return chain.filter(exchange);
		}

		String username = usernameCookie.getValue();
		String authToken = authCookie.getValue();

		if (username == null || !jwtTokenUtil.validateToken(authToken)) {
			log.atInfo().log("Username null or authentication failed: {}", username);
			return chain.filter(exchange);
		}
		log.atInfo().log("Authentication successful. User should get the access: {}", username);
		return userRepository.findUserByUsername(username).flatMap(user -> {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					user.getUsername(), 
					user.getPassword(),
					DataUtil.getGrantedAuthoritiesFromRoles(user.getRoles()));
			SecurityContext securityContext = new SecurityContextImpl(authentication);
			return chain
					.filter(exchange)
					.contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
		});
	}

}
