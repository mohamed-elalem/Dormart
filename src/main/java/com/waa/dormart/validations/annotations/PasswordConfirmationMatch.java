package com.waa.dormart.validations.annotations;

import com.waa.dormart.validations.impl.PasswordConfirmationMatchValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConfirmationMatchValidation.class)
public @interface PasswordConfirmationMatch {
    String message() default "{model.passwordConfirmationMismatch.error}";
    Class<?>[] groups() default {};

    public abstract Class<? extends Payload> [] payload() default {};
}
