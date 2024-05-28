package com.swiss.bank.user.service.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.swiss.bank.user.service.entities.UserProfile;

public interface UserProfileRepository extends ReactiveMongoRepository<UserProfile, String> {

}