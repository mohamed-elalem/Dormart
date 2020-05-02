package com.waa.dormart.services.impl;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.Role;
import com.waa.dormart.repositories.RoleRepository;
import com.waa.dormart.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findAllRolesIn(RoleEnum... roles) {
        List<String> roleNames = Arrays.asList(roles).stream()
                .map(role -> role.roleName())
                .collect(Collectors.toList());

        return roleRepository.findAllIn(roleNames);
    }
}
