package com.activity.activitycalendar.model;

import com.activity.activitycalendar.entity.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ActivityDto(int activityId,String activityDetails,
                          @JsonFormat(pattern = "dd/MM/yyyy") LocalDate activityDate,
                          ActivityStatus activityStatus,
                          Long assignedTo, String description) {
}
