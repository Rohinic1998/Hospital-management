package com.hospital.mapper;

import com.hospital.dto.RegisterRequest;
import com.hospital.dto.UserResponse;
import com.hospital.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);

}
