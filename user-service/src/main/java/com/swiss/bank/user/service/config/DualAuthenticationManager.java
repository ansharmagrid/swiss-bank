package com.swiss.bank.user.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.swiss.bank.user.service.exceptions.InvalidUsernamePasswordException;
import com.swiss.bank.user.service.repositories.UserRepository;
import com.swiss.bank.user.service.util.DataUtil;

import reactor.core.publisher.Mono;

@Component
public class DualAuthenticationManager implements ReactiveAuthenticationManager{

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		
		return userRepository
					.findUserByUsername(username)
					.switchIfEmpty(Mono.error(new InvalidUsernamePasswordException("Invalida username/password exception")))
					.map(user -> {
						if(!passwordEncoder.matches(password, user.getPassword())) {
							throw new InvalidUsernamePasswordException("for user: "+user.getUsername());
						}
						return new UsernamePasswordAuthenticationToken(
								user.getUsername(), 
								user.getPassword(),
								DataUtil.getGrantedAuthoritiesFromRoles(user.getRoles()));
					});
	}

}
