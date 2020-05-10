package com.waa.dormart.services;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> findAllInactiveByRole(RoleEnum role);

    List<User> findAllActiveByRole(RoleEnum role);

    void activateUser(Long id);

    void toggleFollow(Long followerId, Long followingId);

    void addPurchasePoints(Long orderId);

    User getUser(Long userId);
}
