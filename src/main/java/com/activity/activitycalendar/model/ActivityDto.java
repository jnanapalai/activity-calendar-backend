package com.activity.activitycalendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.activity.activitycalendar.entity.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {

    private int activityId;
    private String activityDetails;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate activityDate;

    private ActivityStatus activityStatus;

    private String description;
}
