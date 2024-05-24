package com.swiss.bank.user.service.services;

import com.swiss.bank.user.service.entities.User;
import com.swiss.bank.user.service.models.LoginRequest;
import com.swiss.bank.user.service.models.RegisterUserRequest;
import com.swiss.bank.user.service.models.RegisterUserResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

	public Flux<User> findAllUsers();

    public Mono<User> findUserByUsername(String username);

    public Mono<LoginRequest> encodePasswordForLoginRequest(LoginRequest loginRequest);

    public Mono<User> validateLoginRequest(LoginRequest loginRequest);
    
    public Mono<RegisterUserResponse> registerUser(RegisterUserRequest registerUserRequest);
	
}
