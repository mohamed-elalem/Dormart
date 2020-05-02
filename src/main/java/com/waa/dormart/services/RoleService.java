package com.waa.dormart.services;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    List<Role> findAllRolesIn(RoleEnum... roles);
}
