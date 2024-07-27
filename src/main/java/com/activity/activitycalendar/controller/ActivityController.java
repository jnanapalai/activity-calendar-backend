package com.activity.activitycalendar.controller;


import lombok.RequiredArgsConstructor;

import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.mapper.ActivityMapper;
import com.activity.activitycalendar.model.ActivityCount;
import com.activity.activitycalendar.model.ActivityDto;
import com.activity.activitycalendar.service.ActivityService;
import com.activity.activitycalendar.service.UserService;
import com.activity.activitycalendar.utility.ActivityUtil;

import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;
    private final ActivityUtil activityUtil;

    @PostMapping(value = "/activity", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityDto> saveActivity(@RequestBody ActivityDto activityDTO) {
       System.out.println("Date value is" + activityDTO.getActivityDate().toString());
       System.out.println("request by user id " + userService
               .getUserByUserNameOrEmail(activityUtil.getLoggedInUser()).getId());
       User user = userService
               .getUserByUserNameOrEmail(activityUtil.getLoggedInUser());
       return ResponseEntity.status(201)
               .body(ActivityMapper.activityMapper.mapActivityToActivityDto
                       (this.activityService.saveActivity(activityDTO,user)));
    }

    @PatchMapping(value = "/activity/{activityId}/complete")
    public ResponseEntity<Void> markActivityCompleted(@PathVariable("activityId") String activityId) throws BadRequestException {
        this.activityService.markActivityCompleted(Integer.parseInt(activityId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/activity/{activityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable int activityId) {
        this.activityService.deleteActivity(activityId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/activity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityDto>> getActivities(@RequestParam(value = "activityDate")
                                                               @DateTimeFormat(pattern = "dd/MM/yyyy")
                                                               LocalDate activityDate) {
        User user = userService
                .getUserByUserNameOrEmail(activityUtil.getLoggedInUser());
        System.out.println("Activity date is" + activityDate);
        return ResponseEntity.ok()
                .body(this.activityService.findActivitiesByActivityDate(activityDate,user));
    }

    @GetMapping(value = "/activity/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityCount>> getActivitiesByCount() {

        User user = userService
                .getUserByUserNameOrEmail(activityUtil.getLoggedInUser());
        return ResponseEntity.ok()
                .body(this.activityService.countTotalActivityByActivityDate(user));
    }

}
