package com.youssef.GridPulse;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GridPulseApplication {

	@PostConstruct
	public void testEnv() {
		System.out.println("JWT = " + System.getenv("JWT_SECRET_KEY"));
	}

	public static void main(String[] args) {
		SpringApplication.run(GridPulseApplication.class, args);
	}

}
