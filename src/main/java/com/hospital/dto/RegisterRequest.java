package com.hospital.dto;

import com.hospital.validation.ValidRole;

import com.hospital.util.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = ErrorMessages.USERNAME_REQUIRED)
    @Pattern(
            regexp = "^[A-Za-z][A-Za-z0-9._]{2,19}$",
            message = ErrorMessages.USERNAME_INVALID
    )
    private String name;

    @NotBlank(message = ErrorMessages.EMAIL_REQUIRED)
    @Email(message = ErrorMessages.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ErrorMessages.PHONE_REQUIRED)
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = ErrorMessages.PHONE_INVALID)
    private String phone;

    @NotBlank(message = ErrorMessages.PASSWORD_REQUIRED)
    @Pattern(
            // At least 8 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = ErrorMessages.PASSWORD_INVALID)
    private String password;



}
