package com.hospital.mapper;

import com.hospital.dto.RegisterRequest;
import com.hospital.dto.StaffRegisterRequest;
import com.hospital.dto.UserResponse;
import com.hospital.entity.Role;
import com.hospital.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-20T13:29:39+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( request.getName() );
        user.email( request.getEmail() );
        user.phone( request.getPhone() );
        user.password( request.getPassword() );

        return user.build();
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.name( user.getName() );
        userResponse.email( user.getEmail() );
        userResponse.phone( user.getPhone() );
        userResponse.role( user.getRole() );

        return userResponse.build();
    }

    @Override
    public User toEntity(StaffRegisterRequest req) {
        if ( req == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( req.getName() );
        user.email( req.getEmail() );
        user.phone( req.getPhone() );
        user.password( req.getPassword() );
        if ( req.getRole() != null ) {
            user.role( Enum.valueOf( Role.class, req.getRole() ) );
        }

        return user.build();
    }
}
