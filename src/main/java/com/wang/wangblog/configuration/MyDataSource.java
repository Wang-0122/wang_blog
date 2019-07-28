package com.wang.wangblog.configuration;

import com.wang.wangblog.utils.EncodeUtils;
import com.zaxxer.hikari.HikariDataSource;

public class MyDataSource extends HikariDataSource {

	public void setPassword(String password) {
		String pwd = "";
		try {
			pwd = EncodeUtils.decrypt(password.substring(16), password.substring(0, 16));
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setPassword(pwd);
	}

}
