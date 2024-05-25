package com.swiss.bank.user.service.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
@RequestMapping("/test")
public class TestController {

	@GetMapping("/get")
	public String get() {
		return "get";
	}
	
	@PostMapping("/post")
	public String post() {
		return "post";
	}
	
	@PutMapping("/put")
	public String put() {
		return "put";
	}
	
	@DeleteMapping("/delete")
	public String delete() {
		return "delete";
	}
}
