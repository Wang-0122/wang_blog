/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.wang.wangblog.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ClassName: IDUtils
 * 
 * @description
 * @author Xinxing Jiang
 * @Date 2017年7月5日
 */
public class IDUtils {

	private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private final static Map<Character, Integer> digitMap = new HashMap<Character, Integer>();

	static {
		for (int i = 0; i < digits.length; i++) {
			digitMap.put(digits[i], (int) i);
		}
	}

	/**
	 * 支持的最大进制数
	 */
	private static final int MAX_RADIX = digits.length;

	/**
	 * 支持的最小进制数
	 */
	private static final int MIN_RADIX = 2;

	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return num2str(hi | (val & (hi - 1)), MAX_RADIX).substring(1);
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			uuid("12312312314asdas" + i);
		}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(uuid("12312312314asdas"));
		System.out.println(uuid("1231231214asdas"));
		System.out.println(uuid("1231232222314asdas"));
		System.out.println(uuid("123123332314asdas"));
		System.out.println(uuid("123124412314asdas"));
	}

	/**
	 * 以62进制（字母加数字）生成19位UUID，最短的UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		UUID uuid = UUID.randomUUID();
		StringBuilder sb = new StringBuilder();
		sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
		sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
		sb.append(digits(uuid.getMostSignificantBits(), 4));
		sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
		sb.append(digits(uuid.getLeastSignificantBits(), 12));
		return sb.toString();
	}

	/**
	 * 以62进制（字母加数字）生成19位UUID，最短的UUID
	 * 
	 * @return
	 */
	public static String uuid(String str) {
		String hex = JavaMD5Hash.md5(str);
		StringBuilder sb = new StringBuilder();
		sb.append(digits(Long.parseLong(hex.substring(0, 12), 16), 12));
		sb.append(digits(Long.parseLong(hex.substring(12, 24), 16), 12));
		sb.append(digits(Long.parseLong(hex.substring(24), 16), 8));
		return sb.toString();
	}

	/**
	 * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
	 * 
	 * @param i
	 * @param radix
	 * @return
	 */
	private static String num2str(long i, int radix) {
		if (radix < MIN_RADIX || radix > MAX_RADIX)
			radix = 10;
		if (radix == 10)
			return Long.toString(i);

		final int size = 65;
		int charPos = 64;

		char[] buf = new char[size];
		boolean negative = (i < 0);

		if (!negative) {
			i = -i;
		}

		while (i <= -radix) {
			buf[charPos--] = digits[(int) (-(i % radix))];
			i = i / radix;
		}
		buf[charPos] = digits[(int) (-i)];

		if (negative) {
			buf[--charPos] = '-';
		}

		return new String(buf, charPos, (size - charPos));
	}

}
