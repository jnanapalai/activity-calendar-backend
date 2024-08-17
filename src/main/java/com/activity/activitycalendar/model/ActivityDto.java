package com.activity.activitycalendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.activity.activitycalendar.entity.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityDto {

    private int activityId;
    private String activityDetails;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate activityDate;

    private ActivityStatus activityStatus;
    private Long assignedTo;

    private String description;
}
