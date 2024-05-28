package com.swiss.bank.user.service.entities;

import java.util.Date;

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
public class UserProfile {

	@Id
	private String userId;
	private BasicInfo basicInfo;
	private Address address;
	private Occupation occupation;
	private Kyc kyc;
	private Date createdAt;
	private Date lastLoginAt;
	private String profilePictureUrl;
	private Preferences preferences;
	private boolean emailVerified;
	private boolean governmentIdVerified;
	private boolean dateofBirthVerified;
	private boolean addressVerified;
	private boolean phoneVerified;
	private boolean nationalityVerified;
	
}
