package com.paulorobertomartins.cleanarch.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.paulorobertomartins.cleanarch")
public class CleanArchApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanArchApplication.class, args);
    }
}
