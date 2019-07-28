package com.wang.wangblog.component.security;

import com.wang.wangblog.model.AdminUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -3948213293101192475L;

	private AdminUser user;

	private List<GrantedAuthority> roles;

	public UserDetailsImpl(AdminUser user) {
		this.user = user;

		// init;
		roles = new ArrayList<GrantedAuthority>();
		if (user != null && !StringUtils.isEmpty(user.getRoles())) {
			String[] rolesArr = user.getRoles().split(",");
			for (String roleStr : rolesArr) {
				roles.add(new SimpleGrantedAuthority(roleStr));
			}
		}
		if (roles.isEmpty()) {
			roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
	}

	public AdminUser getUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return this.user != null ? this.user.getLoginPassword() : null;
	}

	@Override
	public String getUsername() {
		return this.user != null ? this.user.getLoginUserName() : null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
