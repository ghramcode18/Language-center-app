package Geeks.languagecenterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class LanguageCenterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanguageCenterAppApplication.class, args);
	}
}


/*

 // Add This Line To Application.properties To Skip NULL VALUES In Responses :
 		spring.jackson.default-property-inclusion=non_null

 */
