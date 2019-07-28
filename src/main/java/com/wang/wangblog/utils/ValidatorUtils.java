package com.wang.wangblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lu_feng
 */
public final class ValidatorUtils {

	public static boolean isURL(String url) {
		return matchingText("(http)s?\\:\\/{2}[0-9a-zA-Z]+\\..+", url);
	}

	public static boolean isEmail(String email) {
		return matchingText("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+", email);
	}

	public static boolean isPhoneNumber(String phoneNumber) {
		return matchingText("[0-9\\(\\)\\.\\-]{7,15}", phoneNumber);
	}

	public static boolean isMoblie(String phone) {
		return matchingText("^\\d{11}$", phone);
	}

	public static boolean isMoblie2(String phone) {
		return matchingText("^\\d{11}$", phone);
	}

	/**
	 * @param phone
	 * @return
	 */
	public static boolean validateMoblie(String phone) {
		int l = phone.length();
		boolean rs = false;
		switch (l) {
		case 7:
			if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4}$", phone)) {
				rs = true;
			}
			break;
		case 11:
			if (matchingText("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$", phone)) {
				rs = true;
			}
			break;
		default:
			rs = false;
			break;
		}
		return rs;
	}

	/**
	 * @param expression
	 * @param text
	 * @return
	 */
	private static boolean matchingText(String expression, String text) {
		Pattern p = Pattern.compile(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		boolean b = m.matches();
		return b;
	}

	public static void main(String[] arg) {

		System.out.println(validateMoblie("18260198979"));

		//
	}

}
