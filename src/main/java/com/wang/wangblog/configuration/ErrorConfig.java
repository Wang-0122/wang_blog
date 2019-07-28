package com.wang.wangblog.configuration;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorConfig implements ErrorPageRegistrar {

	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		ErrorPage e403 = new ErrorPage(HttpStatus.FORBIDDEN, "/page/403");
		ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/page/404");
		ErrorPage e500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/page/500");
		registry.addErrorPages(e403, e404, e500);

	}
}
