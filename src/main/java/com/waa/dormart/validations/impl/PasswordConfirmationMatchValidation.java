package com.waa.dormart.validations.impl;

import com.waa.dormart.models.User;
import com.waa.dormart.validations.annotations.PasswordConfirmationMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmationMatchValidation implements ConstraintValidator<PasswordConfirmationMatch, User> {

    @Override
    public void initialize(PasswordConfirmationMatch constraintAnnotation) {

    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user.getId() == null && !user.getPassword().equals(user.getPasswordConfirmation())) {
            return false;
        }

        return true;
    }
}
