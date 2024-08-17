package com.activity.activitycalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ActivityCalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityCalendarApplication.class, args);
	}

}
