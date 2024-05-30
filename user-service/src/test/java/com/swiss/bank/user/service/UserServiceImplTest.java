package com.swiss.bank.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.swiss.bank.user.service.entities.Role;
import com.swiss.bank.user.service.entities.User;
import com.swiss.bank.user.service.repositories.RoleRepository;
import com.swiss.bank.user.service.repositories.UserRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
class UserServiceImplTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Test
	void testGetUserAndUpdateRoles() {
		Mono<Void> updateUserRoles = userRepository.findUserByUsername("anubhav_sharma").flatMap(user -> {
			Role role = new Role();
			role.setUsername(user.getUsername());
			role.setRoleName("OWNER");
			role.setPrivileges(List.of("STAFF", "ADMIN"));
			return roleRepository.save(role).flatMap(r -> {
				user.setRoles(List.of(r));
				return userRepository.save(user).then();
			});
		});

		StepVerifier.create(updateUserRoles).expectSubscription().verifyComplete();

		Mono<User> updatedUser = userRepository.findUserByUsername("anubhav_sharma");

		StepVerifier.create(updatedUser)
				.expectNextMatches(user -> user.getRoles()
						.stream()
						.anyMatch(role -> "OWNER".equals(role.getRoleName()) && role.getPrivileges().containsAll(List.of("STAFF", "ADMIN"))))
				.verifyComplete();
	}

	@Test
	void testFetchUser() {
		StepVerifier.create(userRepository.findUserByUsername("anubhav_sharma")).assertNext(user -> {
			assertThat(user.getRoles()).isEmpty();
		})
		.verifyComplete();
	}
}
