package com.waa.dormart.seeds;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.Role;
import com.waa.dormart.repositories.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class RoleSeed implements ApplicationRunner {

    private RoleRepository roleRepository;

    public RoleSeed(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Arrays.asList(RoleEnum.values())
            .forEach(role -> {
                if (roleRepository.findByName(role.toString()).isEmpty()) {
                    roleRepository.save(Role.create().withName(role.toString()).build());
                }
            });
    }
}
