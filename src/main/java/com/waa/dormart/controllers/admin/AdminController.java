package com.waa.dormart.controllers.admin;

import com.waa.dormart.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }
}
