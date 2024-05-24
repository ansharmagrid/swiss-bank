package com.swiss.bank.user.service.services;

import com.swiss.bank.user.service.entities.User;

import reactor.core.publisher.Flux;

public interface MissingUserDetailsService {

	public Flux<User> findUsersWithIncompleteInfo();
	
	public Flux<User> findUsersWithZeroAccount();
	
}
