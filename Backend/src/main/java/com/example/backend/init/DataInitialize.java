package com.example.backend.init;

import com.example.backend.entity.Role;
import com.example.backend.entity.Users;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataInitialize implements EntityInitialize, CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void initializeRoles() {
        if (roleRepository.count() == 0) {
            List<String> roleNames = Arrays.asList("ADMIN", "STAFF", "CUSTOMER");

            for (String name : roleNames) {
                Role role = new Role();
                role.setName(name);
                roleRepository.save(role);
            }

            System.out.println("Role đã được khởi tạo thành công!");
        } else {
            System.out.println("Role đã có dữ liệu, không cần khởi tạo lại.");
        }
    }

    @Override
    public void initializeUsersAndUserRoles() {
        if (userRepository.count() == 0) {
            String password = passwordEncoder.encode("Datn12345");

            Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
            Role staffRole = roleRepository.findByName("STAFF").orElse(null);
            Role customerRole = roleRepository.findByName("CUSTOMER").orElse(null);

            List<String> names = Arrays.asList("Ly", "Thang", "Thien", "Quan", "Kiet", "My");

            for (String name : names) {
                Users userAdmin = new Users(name + "Admin", password, name.toLowerCase() + "admin@gmail.com", true);
                userAdmin.setRoles(new HashSet<>(List.of(adminRole)));

                Users userStaff = new Users(name + "Staff", password, name.toLowerCase() + "staff@gmail.com", true);
                userStaff.setRoles(new HashSet<>(List.of(staffRole)));

                Users userCustomer = new Users(name + "Customer", password, name.toLowerCase() + "customer@gmail.com", true);
                userCustomer.setRoles(new HashSet<>(List.of(customerRole)));

                userRepository.saveAll(Arrays.asList(userAdmin, userStaff, userCustomer));
            }

            System.out.println("User và UserRole đã được khởi tạo thành công!");
        } else {
            System.out.println("User đã có dữ liệu, không cần khởi tạo lại.");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeUsersAndUserRoles();
    }
}