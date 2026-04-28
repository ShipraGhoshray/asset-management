package com.selflearning.mapper;

import com.selflearning.dto.UserResponseDto;
import com.selflearning.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toResponseDto(User entity);
    User toEntity(UserResponseDto requestDto);
}