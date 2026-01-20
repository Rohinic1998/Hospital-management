package com.hospital.controller;

import com.hospital.dto.*;
import com.hospital.service.AuthService;
import com.hospital.util.ApiResponse;
import com.hospital.util.ErrorMessages;
import com.hospital.util.SuccessMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register/patient")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest req) {

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(SuccessMessage.USER_REGISTERED_SUCCESSFULLY);
        response.setData(service.registerPatient(req));
        response.setStatus(HttpStatus.CREATED.value());
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    @PostMapping("/register/staff")
    public ResponseEntity<ApiResponse<UserResponse>> registerStaff(
            @Valid @RequestBody StaffRegisterRequest req) {

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Staff Registered Successfully");
        response.setData(service.registerStaff(req));
        response.setStatus(HttpStatus.CREATED.value());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {

        ApiResponse<AuthResponse> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(SuccessMessage.LOGIN_SUCCESSFUL);
        response.setData(service.login(req));
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout/{userId}")
    public ResponseEntity<ApiResponse<String>> logout(@PathVariable Long userId) {
        ApiResponse<String> response = new ApiResponse<>();
        if (userId == null) {
            response.setSuccess(false);
            response.setMessage(ErrorMessages.USER_ID_REQUIRED);   // error message
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setData(null);
            return ResponseEntity.badRequest().body(response);
        }
        service.logout(userId);
        response.setSuccess(true);
        response.setMessage(SuccessMessage.LOG_OUT);
        response.setData("User logged out");
        response.setStatus(HttpStatus.OK.value());
        response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}
