package com.waa.dormart.controllers;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.exceptions.UnuthorizedException;
import com.waa.dormart.models.User;
import com.waa.dormart.services.RoleService;
import com.waa.dormart.services.UserService;
import com.waa.dormart.util.RoleUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping
public class UserController {
    private UserService userService;
    private RoleService roleService;
    private List<String> allowedRoles;
    private RoleUtil roleUtil;

    public UserController(UserService userService, RoleService roleService, RoleUtil roleUtil) {
        this.userService = userService;
        this.allowedRoles = List.of(RoleEnum.SELLER.roleName(), RoleEnum.BUYER.roleName());
        this.roleService = roleService;
        this.roleUtil = roleUtil;
    }

    @GetMapping("register")
    public String getUserForm(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRolesIn(RoleEnum.SELLER, RoleEnum.BUYER));
        return "users/registration";
    }

    @PostMapping("register")
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (!allowedRoles.contains(user.getRole().getName())) {
            throw new UnuthorizedException(HttpStatus.UNAUTHORIZED, String.format("Role %s is not allowed", user.getRole().getName()));
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.findAllRolesIn(RoleEnum.SELLER, RoleEnum.BUYER));
            return "users/registration";
        }

        userService.register(user);

        return "redirect:/";
    }

    @GetMapping("login")
    public String loginForm(@AuthenticationPrincipal User user) {
        return user == null ? "users/login" : "redirect:/";
    }
}
