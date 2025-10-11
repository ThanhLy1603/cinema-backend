package com.example.backend.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World");
    }
}
