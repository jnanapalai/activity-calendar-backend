package com.activity.activitycalendar.mapper;

import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.model.UserDto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User mapUserDtoToUser(UserDto userDto);
}
