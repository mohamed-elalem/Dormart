package com.waa.dormart.services;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.User;

import java.util.List;

public interface UserService {
    User save(User user, RoleEnum roleEnum);
    User register(User user, RoleEnum roleEnum);

    List<User> findAllInactiveByRole(RoleEnum role);
    void activateUser(Long id);
}
