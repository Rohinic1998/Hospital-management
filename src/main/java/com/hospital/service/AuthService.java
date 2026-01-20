package com.hospital.service;

import com.hospital.dto.*;
import com.hospital.entity.Role;
import com.hospital.entity.User;
import com.hospital.exception.CustomException;
import com.hospital.mapper.UserMapper;
import com.hospital.repository.UserRepository;
import com.hospital.security.JwtUtil;
import com.hospital.util.ErrorMessages;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface AuthService {

    UserResponse registerPatient(RegisterRequest req);

    UserResponse registerStaff(StaffRegisterRequest req);

    // ================= Authentication =================
    AuthResponse login(LoginRequest req);

    void logout(long userId);
}
