package com.swiss.bank.user.service.util;

public class DataUtil{

	public static <T> T getOrDefault(T currentValue, T defaultValue) {
		if(currentValue==null) return defaultValue;
		return currentValue;
	}
}
