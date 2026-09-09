package com.mishri.answer_service;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class AnswerServiceApplication {

    public static void main(String[] args) {

        // configuring .env file before application starts
        Dotenv dotenv = Dotenv.configure()
                .directory("services/answer-service")
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach((DotenvEntry entry) -> System.setProperty(entry.getKey(), entry.getValue()));


        SpringApplication.run(AnswerServiceApplication.class, args);
    }

}
