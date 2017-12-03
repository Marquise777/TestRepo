package com.googleit.test.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.googleit.test")
public class GoogleItApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleItApplication.class, args);
	}
}
