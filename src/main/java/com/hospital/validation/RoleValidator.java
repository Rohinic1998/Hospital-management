package com.hospital.validation;

import com.hospital.entity.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false; // null or blank is invalid
        }

        try {
            // Convert to uppercase to make validation case-insensitive
            Role.valueOf(value.toUpperCase());
            return true; // valid enum
        } catch (IllegalArgumentException e) {
            return false; // invalid enum
        }
    }
}
