package com.waa.dormart.util;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.Role;
import com.waa.dormart.services.RoleService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleUtil {
    private List<String> allowedRoles;
    private RoleService roleService;

    public RoleUtil(RoleService roleService) {
        allowedRoles = List.of(RoleEnum.SELLER.roleName(), RoleEnum.BUYER.roleName());
        this.roleService = roleService;
    }

    public List<Role> getAllowedRoles() {
        return roleService.findAll()
                .stream()
                .filter(role -> {
                    return allowedRoles.contains(role.getName());
                }).collect(Collectors.toList());
    }
}
