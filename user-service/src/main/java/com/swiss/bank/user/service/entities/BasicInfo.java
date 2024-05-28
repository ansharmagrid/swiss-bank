package com.swiss.bank.user.service.entities;

import java.util.Date;

import com.swiss.bank.user.service.definitions.Gender;
import com.swiss.bank.user.service.definitions.Nationality;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicInfo {

	private String firstName;
	private String lastName;
	private String fullName;
	private String email;
	private Date dateofBirth;
	private int age;
	private Gender gender;
	private String phone;
	private String secondaryEmail;
	private String secondaryPhone;
	private Nationality nationality;
	
}
