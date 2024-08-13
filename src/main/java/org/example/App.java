package org.example;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
@Log4j2
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
