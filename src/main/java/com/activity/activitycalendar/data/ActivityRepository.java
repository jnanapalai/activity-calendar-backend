package com.activity.activitycalendar.data;

import com.activity.activitycalendar.entity.Activity;
import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.model.ActivityCount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findActivitiesByActivityDateAndUser(LocalDate activityDate, User user);


    @Query("SELECT new com.activity.activitycalendar.model.ActivityCount(a.activityDate, COUNT(a.activityDate)) "
            + "FROM Activity AS a WHERE a.user = :user GROUP BY a.activityDate ORDER BY a.activityDate DESC")
    List<ActivityCount> countTotalActivityByActivityDate(User user);
}
