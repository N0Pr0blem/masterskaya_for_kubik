package com.kubik.masterskaya.mapper;

import com.kubik.masterskaya.dto.user.UserResponseDto;
import com.kubik.masterskaya.entity.User;
import com.kubik.masterskaya.mapper.base.Mappable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserResponseDto> {
}
