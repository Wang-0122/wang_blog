package com.wang.wangblog.configuration;

import com.wang.wangblog.component.security.CustomUserService;
import com.wang.wangblog.utils.JavaMD5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	public static void main(String[] args) {
		System.out.println(JavaMD5Hash.md5(JavaMD5Hash.md5("admin", "@#$@@#$%haui71yyyhyu")));
	}

	@Bean
    UserDetailsService customUserService() {
		return new CustomUserService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				String md5 = JavaMD5Hash.md5((String) rawPassword);
				return md5;
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				System.out.println(">>>>>>>>>>From Database encodedPassword:" + encodedPassword);
				System.out.println(">>>>>>>>>>From Console rawPassword:" + encode(rawPassword));
				return encodedPassword.equals(encode((String) rawPassword));
			}

		});
	}

	private AuthenticationSuccessHandler hanlder = new AuthenticationSuccessHandler() {
		@Override
		public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2) throws IOException, ServletException {
			logger.debug("Login success!");
			arg0.getSession().setAttribute("timezoneoffset", arg0.getParameter("timezoneoffset"));
			arg1.sendRedirect(arg0.getContextPath() + "/admin/index");
		}
	};

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//				.antMatchers("/static/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
//				.antMatchers("/**").hasAnyRole("ADMIN", "USER", "APP_USER")
				.anyRequest().permitAll()
				.and().headers().frameOptions().disable()
				.and().formLogin().loginPage("/login").failureUrl("/login?error").successHandler(hanlder).permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
				.and().csrf().ignoringAntMatchers("/**");
	}
	// @formatter:on

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/static/**");
//	}

}
