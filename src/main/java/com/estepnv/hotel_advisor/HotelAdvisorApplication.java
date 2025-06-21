package com.estepnv.hotel_advisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HotelAdvisorApplication {
	public static void main(String[] args) {
		SpringApplication.run(HotelAdvisorApplication.class, args);
	}
}
