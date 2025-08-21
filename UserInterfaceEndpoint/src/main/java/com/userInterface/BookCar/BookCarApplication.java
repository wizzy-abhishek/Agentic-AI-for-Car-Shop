package com.userInterface.BookCar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookCarApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCarApplication.class, args);
	}

}
