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
public class Preferences {
	@Id
	private String id;
	private String useDarkTheme;
}
