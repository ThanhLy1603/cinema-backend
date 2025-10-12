package com.example.backend.init;

import com.example.backend.entity.Role;
import com.example.backend.entity.Users;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitialize implements EntityInitialize, CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void RoleInitialize() {
        if (roleRepository.count() == 0) {
            String[] roleName = {"ADMIN", "STAFF", "CUSTOMER"};

            for (String name : roleName) {
                Role role = new Role();
                role.setName(name);
                roleRepository.save(role);
            }

            System.out.println("Role đã được khởi tạo thành công");
        } else {
            System.out.println("Role đã có dữ liệu rồi, không cần khởi tạo");
        }
    }

    @Override
    public void UserInitialize() {
        if (userRepository.count() == 0) {
            Role roleAdmin = roleRepository.findByName("ADMIN").orElse(null);
            Role roleUser = roleRepository.findByName("CUSTOMER").orElse(null);
            Role roleStaff = roleRepository.findByName("STAFF").orElse(null);

            System.out.println("Role Admin: " + roleAdmin);
            System.out.println("Role User: " + roleUser);
            System.out.println("Role Staff: " + roleStaff);

            List<String> users = Arrays.asList("Ly", "Thang", "Thien", "Quan", "Kiet", "My");
            String password = passwordEncoder.encode("Datn12345");

            for (String name : users) {
                Users userAdmin = new Users(name + "ADMIN", password, name.toLowerCase() + "@gmail.com", true);
                Users userStaff = new Users(name + "STAFF", password, name.toLowerCase() + "@gmail.com", true);
                Users userCustomer = new Users(name + "CUSTOMER", password, name.toLowerCase() + "@gmail.com", true);

                userRepository.saveAll(Arrays.asList(userAdmin, userStaff, userCustomer));
            }

            System.out.println("User đã được khởi tạo thành công");
        } else {
            System.out.println("User đã có dữ liệu rồi. Không cần khởi tạo");
        }
    }

    @Override
    public void UserRoleInitialize() {
        List<Users> allUsers = userRepository.findAll();

        if (allUsers.isEmpty()) {
            System.out.println("Chưa có User nào để thêm role. Vui lòng tạo User trước");
            return;
        }

        Role roleAdmin = roleRepository.findByName("ADMIN").orElse(null);
        Role roleCustomer = roleRepository.findByName("CUSTOMER").orElse(null);
        Role roleStaff = roleRepository.findByName("STAFF").orElse(null);

        if (roleAdmin == null || roleCustomer == null || roleStaff == null) {
            System.out.println("Không tìm thấy role. Vui lòng khởi tạo role");
            return;
        }

        for (Users user : allUsers) {
            if (user.getUsername().endsWith("STAFF")) {
                user.getRoles().add(roleStaff);
            } else if (user.getUsername().endsWith("ADMIN")) {
                user.getRoles().add(roleAdmin);
            } else if (user.getUsername().endsWith("CUSTOMER")) {
                user.getRoles().add(roleCustomer);
            }

            userRepository.save(user);
        }

        System.out.println("UserRole đã khởi tạo thành công");
    }

    @Override
    public void run(String... args) throws Exception {
        RoleInitialize();
        UserInitialize();
        UserRoleInitialize();
    }
}
