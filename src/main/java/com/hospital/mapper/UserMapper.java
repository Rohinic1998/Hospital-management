package com.hospital.mapper;

import com.hospital.dto.RegisterRequest;
import com.hospital.dto.StaffRegisterRequest;
import com.hospital.dto.UserResponse;
import com.hospital.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //For Patient
    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);
    //For Staff
    User toEntity(StaffRegisterRequest req);
}
