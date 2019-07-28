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

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: StringUtils
 * 
 * @description
 * @author framework
 * @Date 2013-4-1
 */
public class StringUtils {

	/**
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return (value == null || value.equals(""));
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		return (value == null || value.toString().equals(""));
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	private static final byte[] HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
			(byte) 'f' };

	public static String getHexString(byte[] raw) throws UnsupportedEncodingException {
		byte[] hex = new byte[2 * raw.length];
		int index = 0;

		for (byte b : raw) {
			int v = b & 0xFF;
			hex[index++] = HEX_CHAR_TABLE[v >>> 4];
			hex[index++] = HEX_CHAR_TABLE[v & 0xF];
		}
		return new String(hex, "UTF-8");
	}

	public static void main(String args[]) throws Exception {
		byte[] byteArray = { (byte) 255, (byte) 254, (byte) 253, (byte) 252, (byte) 251, (byte) 250 };

		System.out.println(StringUtils.getHexString(byteArray));

		/*
		 * output : fffefdfcfbfa
		 */

	}

	public static String toUpperFirstChar(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String join(List<String> list, String separator) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		return sb.toString().substring(0, sb.toString().length() - separator.length());
	}

}
