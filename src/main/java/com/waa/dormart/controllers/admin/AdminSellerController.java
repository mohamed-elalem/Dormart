package com.waa.dormart.controllers.admin;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.User;
import com.waa.dormart.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/sellers")
public class AdminSellerController {
    private UserService userService;

    public AdminSellerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listSellers(Model model) {
        List<User> sellers = this.userService.findAllInactiveByRole(RoleEnum.SELLER);
        model.addAttribute("sellers", sellers);
        return "admin/sellers/sellers-activation-request";
    }

    @PostMapping("{id}/activate")
    public String activateSeller(@PathVariable Long id) {
        userService.activateUser(id);
        return "redirect:/admin/sellers";
    }
}
