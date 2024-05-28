package com.swiss.bank.user.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.swiss.bank.user.service.entities.Role;
import com.swiss.bank.user.service.entities.User;
import com.swiss.bank.user.service.repositories.UserRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
public class UserServiceImplTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testGetUserAndUpdateRoles() {
		Mono<Void> updateUserRoles = userRepository.findUserByUsername("anubhav_sharma").flatMap(user -> {
			Role role = new Role();
			role.setRoleName("OWNER");
			role.setPrivileges(List.of(
				"STAFF", 
				"ADMIN"
			));
			user.setRoles(List.of(role));
			return userRepository.save(user).then();
		});

		StepVerifier.create(updateUserRoles).expectSubscription().verifyComplete();

		Mono<User> updatedUser = userRepository.findUserByUsername("anubhav_sharma");

		StepVerifier.create(updatedUser)
				.expectNextMatches(user -> user.getRoles()
						.stream()
						.anyMatch(role -> "OWNER".equals(role.getRoleName()) && role.getPrivileges().containsAll(List.of("STAFF", "ADMIN"))))
				.verifyComplete();
	}
}
