package com.swiss.bank.user.service.services.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.swiss.bank.user.service.entities.User;
import com.swiss.bank.user.service.exceptions.InvalidUsernamePasswordException;
import com.swiss.bank.user.service.models.LoginRequest;
import com.swiss.bank.user.service.models.LoginResponse;
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
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public Mono<LoginResponse> login(LoginRequest loginRequest, ServerWebExchange exchange) {
		return userRepository
			.findUserByUsername(loginRequest.getUsername())
			.switchIfEmpty(Mono.error(new InvalidUsernamePasswordException("Invalida username/password exception")))
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
						.maxAge(Duration.ofHours(1))
						.build());
				exchange.getResponse().addCookie(ResponseCookie
						.from("username",auth.getPrincipal().toString())
						.httpOnly(true)
						.secure(true)
						.path("/")
						.maxAge(Duration.ofHours(1))
						.build());
				
			})
			.map(auth -> LoginResponse
							.builder()
							.build());

	}

	@Override
	public Mono<RegisterUserResponse> register(RegisterUserRequest userRegistrationRequest) {
		return userRepository
				.save(User
						.builder()
						.username(userRegistrationRequest.getUsername())
						.password(encoder.encode(userRegistrationRequest.getPassword()))
						.email(userRegistrationRequest.getEmail())
						.build())
				.map(user -> RegisterUserResponse
								.builder()
								.username(user.getUsername())
								.id(user.getId())
								.build());
	}

	@Override
	public Mono<LogoutResponse> logout(ServerWebExchange exchange) {
		exchange.getResponse().addCookie(ResponseCookie
				.from("auth_token","")
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(Duration.ofSeconds(0))
				.build());
		exchange.getResponse().addCookie(ResponseCookie
				.from("username","")
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(Duration.ofSeconds(0))
				.build());
		return Mono.just(LogoutResponse.builder().message("success").build());
	}

}
