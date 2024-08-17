package com.activity.activitycalendar.event.listener;


import lombok.RequiredArgsConstructor;

import com.activity.activitycalendar.event.ActivityCreated;
import com.activity.activitycalendar.service.EmailService;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ActivityListener {

    private final EmailService emailService;

    @EventListener
    public void handleEventActivityCreatedListener(ActivityCreated activityCreated) {
        if (Objects.nonNull(activityCreated.getActivityAssignedToEmail())) {
            this.emailService.sendSimpleEmail(activityCreated.getActivityAssignedToEmail(),
                    activityCreated.getActivityName(),
                    activityCreated.getActivityDescription());
        }
    }
}
