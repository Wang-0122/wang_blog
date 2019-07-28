package com.wang.wangblog.utils;

import java.security.MessageDigest;

public class JavaMD5Hash {

	private final static String SALT_PASS = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static void main(String[] args) {

		// String password = "what'sup man what could I help u";
		//
		// System.out.println("MD5 in hex: " + md5(password));
		//
		// System.out.println("MD5 in hex: " + md5(null));
		// // = d41d8cd98f00b204e9800998ecf8427e
		//
		// System.out.println("MD5 in hex: " + md5("The quick brown fox jumps over the lazy dog"));
		// = 9e107d9d372bb6826bd81d3542a419d6

		System.out.println("MD5 in hex: " + md5("avScNpwPyYMxAk2bkvmUu6O15Bh8jGVu5AbLpYwj7y5I3BLmv23+g9Bxab/tmfc+tn3FQpJPQT4B8Zy2KBsqhA==", "w3lyzfwlh5eqg0g6kz0z5nl3bs9i48e4"));

	}

	public static String md5(String input) {
		return md5(input, SALT_PASS);
	}

	public static String md5(String input, String salt) {
		if (null == input) {
			return null;
		}
		if (salt != null) {
			input += salt;
		}
		return _md5(input, "utf-8");
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static String _md5(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

}