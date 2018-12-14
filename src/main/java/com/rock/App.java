package com.rock;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "com.rock")
@Slf4j
public class App {

	public static void main(String[] args) {
		String[] activeProfiles = SpringApplication.run(App.class, args)
				.getEnvironment().getActiveProfiles();
		Arrays.stream(activeProfiles).forEach(profile->{
			log.warn("Spring Boot 使用Profile为:{}" , profile);
		});
		log.info("SpringBoot Start Success");
	}
}
