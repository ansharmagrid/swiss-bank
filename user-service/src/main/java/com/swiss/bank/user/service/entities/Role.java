package com.swiss.bank.user.service.entities;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

	@Id
	private String id;
	private String roleName;
	private List<String> privileges;
	
}
