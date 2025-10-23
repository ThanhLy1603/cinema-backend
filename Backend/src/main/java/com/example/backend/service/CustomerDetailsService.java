package com.example.backend.service;

import com.example.backend.entity.Users;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        Users user = null;

        if (input.contains("@")) {
            user = userRepository.findByEmail(input)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + input));
        }
        else {
            user = userRepository.findByUsername(input)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + input));
        }

        var authorities = user.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                authorities);
    }
}
