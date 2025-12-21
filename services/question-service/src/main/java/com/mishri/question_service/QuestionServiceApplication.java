package com.mishri.question_service;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuestionServiceApplication {

	public static void main(String[] args) {
		// configuring .env file before application starts
		Dotenv dotenv = Dotenv.configure()
				.directory("services/question-service")
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach((DotenvEntry entry) -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(QuestionServiceApplication.class, args);
	}

}
