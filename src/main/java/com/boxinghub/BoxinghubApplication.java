package com.boxinghub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoxinghubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoxinghubApplication.class, args);
	}
}