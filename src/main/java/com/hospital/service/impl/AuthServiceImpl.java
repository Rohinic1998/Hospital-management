package com.hospital.service.impl;

import com.hospital.dto.*;
import com.hospital.entity.Role;
import com.hospital.entity.User;
import com.hospital.exception.CustomException;
import com.hospital.mapper.UserMapper;
import com.hospital.repository.UserRepository;
import com.hospital.security.JwtUtil;
import com.hospital.service.AuthService;
import com.hospital.util.ErrorMessages;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;
    private final UserMapper mapper;

    public AuthServiceImpl(UserRepository repo, PasswordEncoder encoder, JwtUtil jwt, UserMapper mapper) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
        this.mapper = mapper;
    }
    @Override
    @PreAuthorize("hasRole('ADMIN','PATIENT')")
    public UserResponse registerPatient(RegisterRequest req) {

        if (repo.existsByEmail(req.getEmail()))
            throw new CustomException(ErrorMessages.EMAIL_EXISTS);

        if (repo.existsByPhone(req.getPhone()))
            throw new CustomException(ErrorMessages.PHONE_EXISTS);
        User user = mapper.toEntity(req);
        user.setRole(Role.PATIENT);
        user.setPassword(encoder.encode(req.getPassword()));

        return mapper.toResponse(repo.save(user));
    }

    // ================= Staff Registration =================
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse registerStaff(StaffRegisterRequest req) {

        if (repo.existsByEmail(req.getEmail()))
            throw new CustomException(ErrorMessages.EMAIL_EXISTS);

        if (repo.existsByPhone(req.getPhone()))
            throw new CustomException(ErrorMessages.PHONE_EXISTS);

        Role role;
        try {
            role = Role.valueOf(req.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorMessages.ROLE_INVALID);
        }

        User user = mapper.toEntity(req);
        user.setRole(role);
        user.setPassword(encoder.encode(req.getPassword()));

        return mapper.toResponse(repo.save(user));
    }

    // ================= Login =================
    @Override
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR','ADMIN','RECEPTIONIST')")
    public AuthResponse login(LoginRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new CustomException(ErrorMessages.USER_NOT_FOUND));


        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new CustomException(ErrorMessages.INVALID_CREDENTIALS);

        if(user.isLoginStatus()) {
            throw new CustomException(ErrorMessages.USER_ALREADY_LOGGED_IN);
        }

        user.setLastLogin(java.time.LocalDateTime.now());
        user.setLoginStatus(true);
        repo.save(user);

        return new AuthResponse(
                jwt.generateToken(user.getEmail(), user.getRole().name()),
                user.getRole().name()
        );

    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void logout(long userId) {
        User user = repo.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorMessages.USER_NOT_FOUND));

        user.setLoginStatus(false);
        repo.save(user);
    }

}
