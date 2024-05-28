package com.swiss.bank.user.service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
	
	private String title;
	private String company;
	private String companyAddress;
	private double salary; 
	private int pincode;
}
