package com.waa.dormart.seeds;

import com.github.javafaker.Faker;
import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.User;
import com.waa.dormart.repositories.RoleRepository;
import com.waa.dormart.repositories.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@Order(5)
public class BuyerSeed implements ApplicationRunner {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public BuyerSeed(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = new Faker();
        User buyer = User.create()
                .withEmail("buyer+1@test.com")
                .withName(faker.name().fullName())
                .withPassword(passwordEncoder.encode("12345678"))
                .withRole(roleRepository.findByName(RoleEnum.BUYER.toString()).get())
                .withEnabled(true)
                .withActive(true)
                .build();

        User seller = userRepository.findAllByRoleAndActive(RoleEnum.SELLER.roleName(), true).get(0);
        buyer.getFollowing().add(seller);

        seller.getFollowers().add(buyer);

        userRepository.save(seller);
    }
}
