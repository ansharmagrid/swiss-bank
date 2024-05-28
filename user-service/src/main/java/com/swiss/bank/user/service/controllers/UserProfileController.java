package com.swiss.bank.user.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swiss.bank.user.service.entities.UserProfile;

import reactor.core.publisher.Mono;

@RestController
@RestControllerAdvice
@RequestMapping("/user-profile")
public class UserProfileController {
	
	@PostMapping("/save")
	public ResponseEntity<Mono<UserProfile>> saveUserProfile(@RequestBody UserProfile userProfile){
		return ResponseEntity.ok().build();
	}

}
