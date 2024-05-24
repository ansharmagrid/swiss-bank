package com.swiss.bank.user.service.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.swiss.bank.user.service.annotation.MailAlert;
import com.swiss.bank.user.service.entities.Role;
import com.swiss.bank.user.service.entities.User;
import com.swiss.bank.user.service.exceptions.InvalidUsernamePasswordException;
import com.swiss.bank.user.service.models.LoginRequest;
import com.swiss.bank.user.service.models.LoginResponse;
import com.swiss.bank.user.service.models.LogoutAllDevicesRequest;
import com.swiss.bank.user.service.models.LogoutAllDevicesResponse;
import com.swiss.bank.user.service.models.LogoutRequest;
import com.swiss.bank.user.service.models.LogoutResponse;
import com.swiss.bank.user.service.models.RegisterUserRequest;
import com.swiss.bank.user.service.models.RegisterUserResponse;
import com.swiss.bank.user.service.services.AuthenticationService;
import com.swiss.bank.user.service.services.UserService;
import com.swiss.bank.user.service.util.DataUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	UserService userService;

	private static final String SECRET_HASHING_KEY = "S3CR3T_K3Y";
	private static final long EXPIRATION_TIME_MILLIS = 1000 * 60 * 10;

	@Override
	@MailAlert
	public Mono<LoginResponse> login(LoginRequest loginRequest) {
		return userService.validateLoginRequest(loginRequest)
				.switchIfEmpty(Mono.error(new InvalidUsernamePasswordException(
						"Invalid username/password: "
								+ loginRequest.getUsername())))
				.flatMap(user -> generateLoginResponse(user));
	}

	@Override
	@MailAlert
	public Mono<RegisterUserResponse> register(
			RegisterUserRequest userRegistrationRequest) {
		return userService.registerUser(userRegistrationRequest);
	}

	@Override
	public Mono<LogoutResponse> logout(LogoutRequest logoutRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<LogoutAllDevicesResponse> logoutFromAllDevices(
			LogoutAllDevicesRequest logoutAllDevicesRequest) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@MailAlert
	public Mono<Boolean> validateLogin(ServerWebExchange exchange) {
		return getAuthTokenFromExchange(exchange)
				.map(authHeader -> validateToken(authHeader));
	}

	@Override
	@MailAlert
	public Mono<Boolean> validateRole(ServerWebExchange exchange,
			List<String> roles) {
		Flux<String> rolesPossesedByUser = userService
				.findUserByUsername("anubhav_sharma")
				.map(user -> user.getRoles()).flatMapIterable(eroles -> eroles)
				.map(role -> role.getRoleName().toUpperCase());
		Flux<String> expectedRoles = Flux.fromIterable(roles);
		Mono<Boolean> found = expectedRoles.map(
				role -> rolesPossesedByUser.filter(r -> r.equals(role)).count())
				.flatMap(role -> role).all(roleCount -> roleCount > 0);

		return validateLogin(exchange).concatWith(found).all(value -> value);
	}

	private LoginResponse assignToken(LoginResponse loginresponse, User user) {
		String jwtToken = Jwts.builder().setSubject(user.getUsername())
				.setIssuer("user-service-admin@swiss-bank.com")
				.addClaims(Map.of("roles",
						DataUtil.getOrDefault(user.getRoles(),
								new ArrayList<>()),
						"username",
						DataUtil.getOrDefault(user.getUsername(), "")))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(
						System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
				.signWith(SignatureAlgorithm.HS512, SECRET_HASHING_KEY)
				.compact();
		loginresponse.setToken(jwtToken);
		return loginresponse;
	}
	
	private Mono<LoginResponse> generateLoginResponse(User user) {
		return Mono
				.just(LoginResponse.builder().username(user.getUsername())
						.nationality(user.getNationality().getLabel()).build())
				.map(response -> assignToken(response, user))
				.map(response -> assignRoles(response, user));
	}
	
	private boolean validateToken(String authHeader) {
		return checkValidJwtToken(authHeader);
	}
	
	private Mono<String> getAuthTokenFromExchange(ServerWebExchange exchange) {
		return Mono.just(exchange)
				.map(ex -> ex.getRequest().getHeaders().entrySet())
				.flatMapIterable(headers -> headers)
				.filter(headerEntries -> headerEntries.getKey()
						.equalsIgnoreCase("authorization"))
				.switchIfEmpty(Mono
						.error(new AuthenticationCredentialsNotFoundException(
								"No auth toke found in header")))
				.take(1).map(headerEntry -> headerEntry.getValue())
				.flatMapIterable(authHeaders -> authHeaders)
				.switchIfEmpty(Mono
						.error(new AuthenticationCredentialsNotFoundException(
								"No auth toke found in header")))
				.next();
	}


	private LoginResponse assignRoles(LoginResponse loginresponse, User user) {
		if (user.getRoles() == null)
			return loginresponse;
		loginresponse.setRoles(
				user.getRoles().parallelStream().map(Role::toString).toList());
		return loginresponse;
	}

	private boolean checkValidJwtToken(String authHeader) {
		return Jwts.parser().setSigningKey(SECRET_HASHING_KEY)
				.parseClaimsJws(authHeader).getBody().getExpiration()
				.after(new Date());

	}

}
