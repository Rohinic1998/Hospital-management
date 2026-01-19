package com.hospital.validation;

import com.hospital.util.ErrorMessages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoleValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {
    String message() default ErrorMessages.ROLE_INVALID;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
