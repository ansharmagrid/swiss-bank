package com.swiss.bank.user.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swiss.bank.user.service.entities.User;
import com.swiss.bank.user.service.services.UserService;

import reactor.core.publisher.Flux;

@RestController
@RestControllerAdvice
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/all")
	public ResponseEntity<Flux<User>> getAllUsers(){
		return ResponseEntity.ok(userService.findAllUsers());
	}
	
}
