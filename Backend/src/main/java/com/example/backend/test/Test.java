package com.example.backend.test;

import com.example.backend.entity.Film;
import com.example.backend.repository.FilmRepository;
import com.example.backend.service.CustomerDetailsService;
import com.example.backend.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Test implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World");
        System.out.println("Hash of Datn12345: " + passwordEncoder.encode("Datn12345"));
    }
}
