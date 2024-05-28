package com.swiss.bank.user.service.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.swiss.bank.user.service.entities.Role;

public class DataUtil{

	public static <T> T getOrDefault(T currentValue, T defaultValue) {
		if(currentValue==null) return defaultValue;
		return currentValue;
	}
	
	public static List<SimpleGrantedAuthority> getGrantedAuthoritiesFromRoles(List<Role> roles){
		if(roles==null) return new ArrayList<>();
		return roles
				.stream()
				.flatMap(role -> role.getPrivileges().parallelStream())
				.map(priv -> new SimpleGrantedAuthority(priv))
				.toList();
	}
}
