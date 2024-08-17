package com.activity.activitycalendar.event.mapper;

import com.activity.activitycalendar.data.UserRepository;
import com.activity.activitycalendar.entity.Activity;
import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.event.ActivityCreated;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import lombok.NoArgsConstructor;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
@MapperConfig
@NoArgsConstructor
@Component
public abstract class ActivityEventMapper {


    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(ActivityEventMapper.class);

    @Mapping(target = "activityId" , source = "id")
    @Mapping(target = "activityName" , source = "activityDetails")
    @Mapping(target = "activityDescription" , source = "description")
    public abstract ActivityCreated entityToActivityCreated(Activity activity);

    @AfterMapping
    public void afterMapping(
            @MappingTarget ActivityCreated activityCreated,
            Activity activity) {
        Long assignedTo = activity.getAssignedTo();
        if (Objects.nonNull(assignedTo)) {
            Optional<User> userOptional = this.userRepository.findById(assignedTo);
            userOptional.ifPresentOrElse(user -> activityCreated.setActivityAssignedToEmail(user.getEmail()),
                    ()-> logger.warn("User not found for id", activity.getAssignedTo() ));
        }
    }
}
