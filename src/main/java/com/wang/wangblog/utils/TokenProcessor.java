package com.wang.wangblog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author lu_feng
 */
public class TokenProcessor {

	public static String generateTokeCode() {
		String value = System.currentTimeMillis() + new Random().nextInt() + "";
		// 获取数据指纹，指纹是唯一的
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] b = md.digest(value.getBytes());// 产生数据的指纹
			// Base64编码
			Base64Utils.encode(b);
			return Base64Utils.encode(b);// 制定一个编码
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
