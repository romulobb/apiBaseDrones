package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.repositories.DroneRepository;

@SpringBootApplication
public class StartApplication {

    // start everything
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    // run this only on profile 'demo', avoid run this in test
    @Profile("demo")
    @Bean
    CommandLineRunner initDatabase(DroneRepository repository) {
       return args -> {
           // repository.save(new Book("Lord of the Rings", "J.R.R. Tolkien", new BigDecimal("25.41")));
           // repository.save(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", new BigDecimal("24.41")));
           // repository.save(new Book("Twenty Thousand Leagues Under the Sea", "Jules Verne", new BigDecimal("15.25")));
        };
    }
}