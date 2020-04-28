package com.waa.dormart.services.impl;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.User;
import com.waa.dormart.repositories.RoleRepository;
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
    private RoleRepository roleRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(User user, RoleEnum roleEnum) {
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName(roleEnum.roleName()).get());
        user.setEnabled(true);
        user = save(user, roleEnum);

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
        return this.userRepository.findAllInactiveByRole(role.roleName());
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
    public User save(User user, RoleEnum roleEnum) {
        user = this.userRepository.save(user);
        return user;
    }
}
