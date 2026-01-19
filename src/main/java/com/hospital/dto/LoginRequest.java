package com.hospital.dto;

import com.hospital.util.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = ErrorMessages.EMAIL_REQUIRED)
    private String email;

    @NotBlank(message = ErrorMessages.PASSWORD_REQUIRED)
    private String password;
}
