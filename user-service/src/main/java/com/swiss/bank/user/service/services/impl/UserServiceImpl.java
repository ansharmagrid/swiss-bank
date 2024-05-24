package com.swiss.bank.user.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swiss.bank.user.service.entities.User;
import com.swiss.bank.user.service.exceptions.InvalidUsernamePasswordException;
import com.swiss.bank.user.service.models.LoginRequest;
import com.swiss.bank.user.service.models.RegisterUserRequest;
import com.swiss.bank.user.service.models.RegisterUserResponse;
import com.swiss.bank.user.service.repositories.UserRepository;
import com.swiss.bank.user.service.services.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserRepository userRepository;

	@Override
	public Flux<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Mono<User> findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public Mono<LoginRequest> encodePasswordForLoginRequest(
			LoginRequest request) {
		return Mono.just(LoginRequest.builder().username(request.getUsername())
				.password(encoder.encode(request.getPassword())).build());
	}

	@Override
	public Mono<User> validateLoginRequest(LoginRequest loginRequest) {
		return userRepository.findUserByUsername(loginRequest.getUsername())
				.filter(user -> encoder.matches(loginRequest.getPassword(),
						user.getPassword()))
				.switchIfEmpty(Mono.error(new InvalidUsernamePasswordException(
						"invalid username/password: "
								+ loginRequest.getUsername())));
	}

	@Override
	public Mono<RegisterUserResponse> registerUser(
			RegisterUserRequest registerUserRequest) {
		return userRepository
				.save(User.builder().username(registerUserRequest.getUsername())
						.address(registerUserRequest.getAddress())
						.dateOfBirth(registerUserRequest.getDateOfBirth())
						.email(registerUserRequest.getEmail())
						.fullName(registerUserRequest.getFullName())
						.gender(registerUserRequest.getGender())
						.identificationType(
								registerUserRequest.getIdentificationType())
						.identificationId(
								registerUserRequest.getIdentificationId())
						.nationality(registerUserRequest.getNationality())
						.password(encoder
								.encode(registerUserRequest.getPassword()))
						.phone(registerUserRequest.getPhone()).build())
				.map(user -> RegisterUserResponse.builder()
						.username(user.getUsername()).build());
	}

}
