package com.wang.wangblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.wang.wangblog.dao")
@SpringBootApplication
public class WangblogApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WangblogApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WangblogApplication.class);
    }
}
