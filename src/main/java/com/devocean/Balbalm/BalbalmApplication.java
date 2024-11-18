package com.devocean.Balbalm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BalbalmApplication {

	public static void main(String[] args) {
		SpringApplication.run(BalbalmApplication.class, args);
	}

}
