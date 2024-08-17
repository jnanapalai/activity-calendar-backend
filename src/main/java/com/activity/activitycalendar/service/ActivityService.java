package com.activity.activitycalendar.service;

import lombok.RequiredArgsConstructor;

import com.activity.activitycalendar.data.ActivityRepository;
import com.activity.activitycalendar.entity.Activity;
import com.activity.activitycalendar.entity.ActivityStatus;
import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.mapper.ActivityMapper;
import com.activity.activitycalendar.model.ActivityCount;
import com.activity.activitycalendar.model.ActivityDto;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public List<ActivityDto> findAllActivity() {
        List<Activity> activities = this.activityRepository.findAll();
        return ActivityMapper.activityMapper
                .mapActivityListToDtoList(this.activityRepository.findAll());
    }

    public Activity saveActivity(ActivityDto activityDto, User user) {
        Activity activity = ActivityMapper.activityMapper.mapActivityDtoToActivity(activityDto);
        activity.setActivityStatus(ActivityStatus.ASSIGNED);
        activity.setUser(user);
        user.setActivityList(List.of(activity));
        return this.activityRepository.save(activity);
    }

    public void markActivityCompleted(Integer activityId) throws BadRequestException {
       Activity activity = this.activityRepository.findById(activityId)
                .orElseThrow(BadRequestException::new);
       activity.setActivityStatus(ActivityStatus.FINISHED);
       this.activityRepository.save(activity);
    }

    public void deleteActivity(Integer activityId) {
        this.activityRepository.deleteById(activityId);
    }
    public List<ActivityDto> findActivitiesByActivityDate(LocalDate activityDate, User user) {
        return  ActivityMapper.activityMapper
                .mapActivityListToDtoList(this.activityRepository.
                        findActivitiesByActivityDateAndUserOrActivityDateAndAssignedTo(
                                activityDate,user,
                                activityDate,
                                user.getId()));
    }

    public List<ActivityCount> countTotalActivityByActivityDate(User user) {
        return activityRepository.countTotalActivityByActivityDate(user);
    }
}
