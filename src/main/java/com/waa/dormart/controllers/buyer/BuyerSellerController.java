package com.waa.dormart.controllers.buyer;

import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.User;
import com.waa.dormart.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("marketplace/sellers")
public class BuyerSellerController {
    private UserService userService;

    public BuyerSellerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSellers(@AuthenticationPrincipal User loggedInUser, Model model) {
        List<User> sellers = userService.findAllActiveByRole(RoleEnum.SELLER);
        model.addAttribute("sellers", sellers);
        model.addAttribute("loggedInUser", loggedInUser);
        return "marketplace/sellers/list";
    }

    @PatchMapping("{id}/toggle-follow")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleFollow(@PathVariable("id") Long sellerId, @AuthenticationPrincipal User loggedInUser) {
        userService.toggleFollow(loggedInUser.getId(), sellerId);
    }

}
