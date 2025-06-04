package com.horte.horte_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.horte"})
@EnableJpaRepositories(basePackages = {"com.horte.repository"})
@EntityScan(basePackages = {"com.horte.model"})
public class HorteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HorteApiApplication.class, args);
    }

}