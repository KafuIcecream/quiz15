package com.example.quiz15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class Quiz15Application {

	
	public static void main(String[] args) {
		SpringApplication.run(Quiz15Application.class, args);
	}

}
