package com.example.backend.test;

import com.example.backend.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Test implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World");
        System.out.println("Hash of Datn12345: " + passwordEncoder.encode("Datn12345"));

        System.out.println("User details: " + customerDetailsService.loadUserByUsername("lycustomer@gmail.com"));
    }
}
