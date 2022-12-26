package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventSourcingCqrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSourcingCqrsApplication.class, args);
		System.out.println("account cqrs started");
	}

}
