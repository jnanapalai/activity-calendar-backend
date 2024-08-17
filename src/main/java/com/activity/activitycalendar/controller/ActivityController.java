package com.activity.activitycalendar.controller;



import com.activity.activitycalendar.entity.Activity;
import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.event.mapper.ActivityEventMapper;
import com.activity.activitycalendar.mapper.ActivityMapper;
import com.activity.activitycalendar.model.ActivityCount;
import com.activity.activitycalendar.model.ActivityDto;
import com.activity.activitycalendar.service.ActivityService;
import com.activity.activitycalendar.service.UserService;
import com.activity.activitycalendar.utility.ActivityUtil;

import org.apache.coyote.BadRequestException;
import org.springframework.context.ApplicationEventPublisher;
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

import lombok.RequiredArgsConstructor;

/**
 * This class responsible for create, get, delete activity
 * for a user
 */
@RestController
@RequiredArgsConstructor
public class ActivityController {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ActivityService activityService;
    private final UserService userService;
    private final ActivityUtil activityUtil;
    private final ActivityEventMapper activityEventMapper;

    /**
     * This method responsible take request from Rest client and
     * create an activity for a specific user on
     * a specific date and year.
     *
     * @param activityDTO instance of ActivityDto
     * @return instance of ResponseEntity of ActivityDto (Details of Created Activity)
     */
    @PostMapping(value = "/activity", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityDto> saveActivity(@RequestBody ActivityDto activityDTO) {
       System.out.println("Date value is" + activityDTO.getActivityDate().toString());
       System.out.println("request by user id " + userService
               .getUserByUserNameOrEmail(activityUtil.getLoggedInUser()).getId());
       User user = userService
               .getUserByUserNameOrEmail(activityUtil.getLoggedInUser());

       Activity activity = this.activityService.saveActivity(activityDTO,user);
       applicationEventPublisher.publishEvent(this.activityEventMapper.entityToActivityCreated(activity));
       return ResponseEntity.status(201)
               .body(ActivityMapper.activityMapper.mapActivityToActivityDto
                       (activity));
    }

    /**
     * This method responsible take request from Rest client and
     * mark activity complete for a user
     *
     * @param activityId activity id
     * @return ResponseEntity with no content
     * @throws BadRequestException throws exception if activity not found
     */
    @PatchMapping(value = "/activity/{activityId}/complete")
    public ResponseEntity<Void> markActivityCompleted(@PathVariable("activityId") String activityId) throws BadRequestException {
        this.activityService.markActivityCompleted(Integer.parseInt(activityId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    /**
     * This method is responsible for taking request from Rest Client and
     * delete activity.
     *
     * @param activityId activity id
     * @return
     */
    @DeleteMapping("/activity/{activityId}")
    public ResponseEntity<Void> deleteActivity(@PathVariable int activityId) {
        this.activityService.deleteActivity(activityId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * This method is responsible for get all activity on a specific date for a user.
     *
     * @param activityDate activityDate
     * @return list of activities
     */
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

    /**
     * This method responsible for take request from Rest client and return
     * total activity count on date wise for a user
     *
     * @return List of activity with activityCount and the activity created date
     */
    @GetMapping(value = "/activity/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityCount>> getActivitiesByCount() {

        User user = userService
                .getUserByUserNameOrEmail(activityUtil.getLoggedInUser());
        return ResponseEntity.ok()
                .body(this.activityService.countTotalActivityByActivityDate(user));
    }

}
