package com.waa.dormart.controllers;

import com.waa.dormart.exceptions.HttpException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(HttpException.class)
    public String handleGenericException(HttpException exception, Model model) {
        model.addAttribute("status", exception.getHttpStatus().toString());
        model.addAttribute("message", exception.getMessage());

        return "errors/generic";
    }
}
