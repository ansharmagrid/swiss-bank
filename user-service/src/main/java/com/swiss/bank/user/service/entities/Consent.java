package com.swiss.bank.user.service.entities;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consent {

	@Id
	private String id;
	private boolean canSendBankingOffers;
	private boolean canSendOtherServiceOffers;
	private boolean canUseDataForTrainingPurpose;
	private boolean canShareDataWithThirdParty;
	private boolean canSendPromotionalAdds;
	private boolean canKeepDataForMoreThan180Days;
	
}
