package com.waa.dormart.services.impl;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.ShoppingOrder;
import com.waa.dormart.models.User;
import com.waa.dormart.repositories.ShoppingOrderRepository;
import com.waa.dormart.repositories.UserRepository;
import com.waa.dormart.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private ShoppingOrderRepository shoppingOrderRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            ShoppingOrderRepository shoppingOrderRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.shoppingOrderRepository = shoppingOrderRepository;
    }

    @Override
    public User register(User user) {
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user = userRepository.save(user);

        try {
            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findAllInactiveByRole(RoleEnum role) {
        return this.userRepository.findAllByRoleAndActive(role.roleName(), false);
    }

    @Override
    public List<User> findAllActiveByRole(RoleEnum role) {
        return this.userRepository.findAllByRoleAndActive(role.roleName(), true);
    }

    @Override
    public void activateUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(true);
            userRepository.save(user);
        }
    }

    @Override
    public void toggleFollow(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).get();
        User following = userRepository.findById(followingId).get();

        if (follower.isFollowing(following)) {
            follower.getFollowing().remove(following);
        } else {
            follower.getFollowing().add(following);
        }

        userRepository.save(follower);
    }

    @Override
    public void addPurchasePoints(Long orderId) {
        ShoppingOrder order = shoppingOrderRepository.getOne(orderId);
        User buyer = order.getBuyer();
        Double deductedPoints = Math.min(order.getPrice(), Math.floor(buyer.getPoints()));
        Double newPrice = order.getPrice() - deductedPoints;
        buyer.setPoints(buyer.getPoints() - deductedPoints + newPrice / 100);
        userRepository.save(buyer);
        order.setPrice(newPrice);
        shoppingOrderRepository.save(order);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.getOne(userId);
    }
}
