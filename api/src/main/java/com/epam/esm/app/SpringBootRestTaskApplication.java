package com.epam.esm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.epam.esm")
public class SpringBootRestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestTaskApplication.class, args);
	}

}
