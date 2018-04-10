package com.github.johnsonmoon.fastorm.mapper.util;

import java.security.SecureRandom;

/**
 * Created by Xuyh at 2016/08/17 下午 02:30.
 */
public class RandomUtils {
	private static SecureRandom random = new SecureRandom();
	private static final String BASE_NUMBER_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz";
	private static final String BASE_NUMBER = "0123456789";

	public static String getRandomString(int length) {
		StringBuilder stringBuffer = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(BASE_NUMBER_CHARACTERS.length());
			stringBuffer.append(BASE_NUMBER_CHARACTERS.charAt(number));
		}
		return stringBuffer.toString();
	}

	public static String getRandomNumberString(int length) {
		StringBuilder stringBuffer = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(BASE_NUMBER.length());
			stringBuffer.append(BASE_NUMBER.charAt(number));
		}
		return stringBuffer.toString();
	}
}
