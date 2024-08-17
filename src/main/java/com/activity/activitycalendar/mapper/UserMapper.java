package com.activity.activitycalendar.mapper;

import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.model.UserDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User mapUserDtoToUser(UserDto userDto);

    @Mapping(target = "userId", source = "id")
    UserDto mapUserToUserDto(User user);

    List<UserDto> mapUserListToDtoList(List<User> user);
}
