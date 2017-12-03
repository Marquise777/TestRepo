package com.dispatcher.test.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.dispatcher.test")
public class DispatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(DispatcherApplication.class, args);
	}
}
