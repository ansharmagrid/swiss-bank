package com.swiss.bank.user.service.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
	private String userId;
	@Nonnull
	@Indexed(unique = true)
	private String username;
	@Nonnull
	private String password;
	@Nonnull
	@Indexed(unique = true)
	private String email;
	@Nonnull
	@Indexed(unique = true)
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
	@Indexed(unique = true)
	private String identificationId;
	@Nonnull
	private List<Role> roles;
	private Date createdAt;
	private Date lastLoginAt;
	private String profilePictureUrl;
	private Preferences preferences;
	private Consent consent;

}
