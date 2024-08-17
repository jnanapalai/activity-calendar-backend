package com.activity.activitycalendar.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.context.ApplicationEvent;


@Builder
@Getter
@Setter
public class ActivityCreated{
    private  long activityId;
    private  String activityName;
    private  String activityDescription;
    private  String activityAssignedToEmail;


}
