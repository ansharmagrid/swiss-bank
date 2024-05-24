package com.swiss.bank.user.service.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.swiss.bank.user.service.definitions.Gender;
import com.swiss.bank.user.service.definitions.IdentificationType;
import com.swiss.bank.user.service.definitions.Nationality;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class User {

	@Id
	private String id;
	@Nonnull
	@Builder.Default
	private String userId = String.valueOf(System.currentTimeMillis());
	@Nonnull
	private String username;
	@Nonnull
	private String password;
	@Nonnull
	private String email;
	@Nonnull
	private String phone;
	@Nonnull
	private String fullName;
	@Nonnull
	private Date dateOfBirth;
	@Nonnull
	private Address address;
	@Nonnull
	private Gender gender;
	@Nonnull
	private Nationality nationality;
	@Nonnull
	private IdentificationType identificationType;
	@Nonnull
	private String identificationId;
	private List<Role> roles;
	private List<Account> accounts;
	private Date createdAt;
	private Date lastLoginAt;
	private String profilePictureUrl;
	private Preferences preferences;
	private Consent consent;

}
