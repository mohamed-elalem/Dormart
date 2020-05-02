package com.waa.dormart.controllers.seller;

import com.waa.dormart.constants.RoleEnum;
import com.waa.dormart.models.User;
import com.waa.dormart.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private UserService userService;

    public SellerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "sellers/new";
    }

//    @PostMapping("register")
//    public String createSeller(
//            @Valid @ModelAttribute("user") User seller,
//            final BindingResult result,
//            Model model) throws Exception {
//        if (result.hasErrors()) {
//            model.addAttribute("user", seller);
//            return "sellers/new";
//        }
//        this.userService.register(seller, RoleEnum.SELLER);
//        return "redirect:/";
//    }
}
