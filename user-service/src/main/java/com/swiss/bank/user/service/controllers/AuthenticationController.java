package com.swiss.bank.user.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import com.swiss.bank.user.service.models.LoginRequest;
import com.swiss.bank.user.service.models.LoginResponse;
import com.swiss.bank.user.service.models.RegisterUserRequest;
import com.swiss.bank.user.service.models.RegisterUserResponse;
import com.swiss.bank.user.service.services.AuthenticationService;
import com.swiss.bank.user.service.services.UserService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RestControllerAdvice
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<Mono<LoginResponse>> handleUserLogin(@Valid @RequestBody LoginRequest loginRequest, ServerWebExchange exchange) {
		return ResponseEntity.ok(authenticationService.login(loginRequest, exchange));
	}

	@GetMapping("/login")
	public String loginPage() {
		return "Please login using POST method with proper credentials";
	}

	@GetMapping("/register")
	public ResponseEntity<Mono<RegisterUserResponse>> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
		return ResponseEntity.ok(authenticationService.register(registerUserRequest));
	}

}
