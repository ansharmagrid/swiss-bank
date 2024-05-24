package com.swiss.bank.user.service.services;

import java.util.List;

import org.springframework.web.server.ServerWebExchange;

import com.swiss.bank.user.service.models.LoginRequest;
import com.swiss.bank.user.service.models.LoginResponse;
import com.swiss.bank.user.service.models.LogoutAllDevicesRequest;
import com.swiss.bank.user.service.models.LogoutAllDevicesResponse;
import com.swiss.bank.user.service.models.LogoutRequest;
import com.swiss.bank.user.service.models.LogoutResponse;
import com.swiss.bank.user.service.models.RegisterUserRequest;
import com.swiss.bank.user.service.models.RegisterUserResponse;

import reactor.core.publisher.Mono;

public interface AuthenticationService {

	public Mono<LoginResponse> login(LoginRequest loginRequest);

	public Mono<RegisterUserResponse> register(
			RegisterUserRequest userRegistrationRequest);

	public Mono<LogoutResponse> logout(LogoutRequest logoutRequest);

	public Mono<LogoutAllDevicesResponse> logoutFromAllDevices(
			LogoutAllDevicesRequest logoutAllDevicesRequest);

	public Mono<Boolean> validateLogin(ServerWebExchange exchange);

	public Mono<Boolean> validateRole(ServerWebExchange exchange, List<String> roles);

}
