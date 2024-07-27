package com.activity.activitycalendar.mapper;

import com.activity.activitycalendar.entity.Activity;
import com.activity.activitycalendar.entity.ActivityStatus;
import com.activity.activitycalendar.model.ActivityDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface ActivityMapper {

    ActivityMapper activityMapper = Mappers.getMapper(ActivityMapper.class);

    @Mapping(target = "activityId", source = "id")
    @Mapping(target = "description", source = "description")
    @ValueMapping(target = "activityStatus", source = "activityStatus")
    ActivityDto mapActivityToActivityDto(Activity activity);


    List<ActivityDto> mapActivityListToDtoList(List<Activity> activities);

    @Mapping(target = "id", source = "activityId")
    @Mapping(target = "description", source = "description")
    Activity mapActivityDtoToActivity(ActivityDto activityDto);
}
