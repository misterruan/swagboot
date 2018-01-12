package com.hl;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@SpringBootApplication
//@ComponentScan
//@Configuration
//@EnableAutoConfiguration
public class App {
	private static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		String[] activeProfiles = SpringApplication.run(App.class, args)
				.getEnvironment().getActiveProfiles();
		Arrays.stream(activeProfiles).forEach(profile->{
			logger.warn("Spring Boot 使用profile为:{}" , profile);
		});
		logger.info("SpringBoot Start Success");
	}
}
