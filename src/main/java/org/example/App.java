package org.example;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
