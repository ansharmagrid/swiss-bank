package com.swiss.bank.user.service.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Address {

	@Id
	private String id;
	private String username;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String landMark;
	private String city;
	private String state;
	private String country;
	private int pincode;
}
