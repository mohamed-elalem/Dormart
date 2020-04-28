package com.waa.dormart.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "index";
    }
}
