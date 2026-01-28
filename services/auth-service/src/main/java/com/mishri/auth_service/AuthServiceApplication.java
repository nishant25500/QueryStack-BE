package com.mishri.auth_service;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {
	public static void main(String[] args) {
		// configuring .env file before application starts
		Dotenv dotenv = Dotenv.configure()
				.directory("services/auth-service")
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach((DotenvEntry entry) -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
