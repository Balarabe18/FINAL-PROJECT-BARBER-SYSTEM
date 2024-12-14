package com.bms.bmsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.bms.bmsproject.entities")  // Only scan entity classes
public class BmsprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsprojectApplication.class, args);
	}

}
