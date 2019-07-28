package com.wang.wangblog.component.security;

import com.wang.wangblog.dao.AdminUserMapper;
import com.wang.wangblog.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService {

	@Autowired
	private AdminUserMapper adminUserMapper;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		AdminUser user = adminUserMapper.findByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		System.out.println("s:" + s);
		System.out.println("username:" + user.getLoginUserName() + ";password:" + user.getLoginPassword());
		UserDetailsImpl userDetails = new UserDetailsImpl(user);
		return userDetails;
	}

}
