package com.waa.dormart.seeds;

import com.github.javafaker.Faker;
import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.Role;
import com.waa.dormart.models.User;
import com.waa.dormart.repositories.RoleRepository;
import com.waa.dormart.repositories.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(2)
public class AdminSeed implements ApplicationRunner {
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public AdminSeed(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = new Faker();
        userRepository.save(User.create()
            .withEmail("admin@test.com")
            .withName(faker.name().fullName())
            .withPassword(passwordEncoder.encode("12345678"))
            .withRole(roleRepository.findByName(RoleEnum.ADMIN.toString()).get())
            .withEnabled(true)
            .build());
    }
}
