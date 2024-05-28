package com.swiss.bank.user.service.entities;

import com.swiss.bank.user.service.definitions.IdentificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kyc {

	private IdentificationType addressProofType;
	private IdentificationType identityProofType;
	private String addressProofId;
	private String identityProofId;
	private String addressProof;
	private String identityProof;
	private String personalPhoto;
	private String emailForVerification;
	private String phoneForVerification;

}
