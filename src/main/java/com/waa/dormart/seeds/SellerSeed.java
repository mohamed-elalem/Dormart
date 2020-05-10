package com.waa.dormart.seeds;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.User;
import com.waa.dormart.repositories.RoleRepository;
import com.waa.dormart.repositories.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class SellerSeed implements ApplicationRunner {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public SellerSeed(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(User.create()
                .withEmail("seller+active@test.com")
                .withName("Active Seller")
                .withPassword(passwordEncoder.encode("12345678"))
                .withRole(roleRepository.findByName(RoleEnum.SELLER.toString()).get())
                .withEnabled(true)
                .withActive(true)
                .build());

        userRepository.save(User.create()
                .withEmail("seller+inactive@test.com")
                .withName("InActive Seller")
                .withPassword(passwordEncoder.encode("12345678"))
                .withRole(roleRepository.findByName(RoleEnum.SELLER.toString()).get())
                .withEnabled(true)
                .withActive(false)
                .build());

        userRepository.save(User.create()
                .withEmail("seller+active2@test.com")
                .withName("Active Seller 2")
                .withPassword(passwordEncoder.encode("12345678"))
                .withRole(roleRepository.findByName(RoleEnum.SELLER.toString()).get())
                .withEnabled(true)
                .withActive(true)
                .build());
    }
}
