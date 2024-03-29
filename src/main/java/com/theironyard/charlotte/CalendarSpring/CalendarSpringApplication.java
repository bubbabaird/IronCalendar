package com.theironyard.charlotte.CalendarSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
		basePackageClasses = { CalendarSpringApplication.class, Jsr310JpaConverters.class }
)

@SpringBootApplication
public class CalendarSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalendarSpringApplication.class, args);
	}
}
