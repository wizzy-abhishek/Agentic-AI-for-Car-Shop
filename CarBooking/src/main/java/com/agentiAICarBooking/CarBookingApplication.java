package com.agentiAICarBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarBookingApplication.class, args);
	}

}
