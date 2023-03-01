package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.model.Drone;
import com.service.DroneService;

@SpringBootApplication
public class StartApplication {

    // start everything
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    // run this only on profile 'demo', avoid run this in test
    @Profile("demo")
    @Bean
    CommandLineRunner initDatabase(DroneService service) {
       return args -> {
          service.registerDrone("serialNumberA","Middleweight");
          service.registerDrone("serialNumberB","Lightweight");
          service.registerDrone("serialNumberC","Middleweight");


        };
    }
}