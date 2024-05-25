package com.swiss.bank.user.service.services.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.swiss.bank.user.service.models.LoginRequest;
import com.swiss.bank.user.service.models.LoginResponse;
import com.swiss.bank.user.service.models.LogoutRequest;
import com.swiss.bank.user.service.models.LogoutResponse;
import com.swiss.bank.user.service.models.RegisterUserRequest;
import com.swiss.bank.user.service.models.RegisterUserResponse;
import com.swiss.bank.user.service.repositories.UserRepository;
import com.swiss.bank.user.service.services.AuthenticationService;
import com.swiss.bank.user.service.util.DataUtil;
import com.swiss.bank.user.service.util.JwtTokenUtil;

import reactor.core.publisher.Mono;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private ReactiveAuthenticationManager authenticationManager;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Mono<LoginResponse> login(LoginRequest loginRequest, ServerWebExchange exchange) {
		return userRepository
			.findUserByUsername(loginRequest.getUsername())
			.flatMap(user -> 
				authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword(), 
						DataUtil.getGrantedAuthoritiesFromRoles(user.getRoles())))
			)
			.doOnNext(auth -> {
				exchange.getResponse().addCookie(ResponseCookie
						.from("auth_token",jwtTokenUtil.generateAuthToken(auth.getPrincipal().toString()))
						.httpOnly(true)
						.secure(true)
						.path("/")
						.maxAge(Duration.ofHours(10))
						.build());
				exchange.getResponse().addCookie(ResponseCookie
						.from("username",auth.getPrincipal().toString())
						.httpOnly(true)
						.secure(true)
						.path("/")
						.maxAge(Duration.ofHours(10))
						.build());
				
			})
			.map(auth -> LoginResponse
							.builder()
							.build());

	}

	@Override
	public Mono<RegisterUserResponse> register(RegisterUserRequest userRegistrationRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<LogoutResponse> logout(LogoutRequest logoutRequest, ServerWebExchange exchange) {
		// TODO Auto-generated method stub
		return null;
	}

}
