package com.activity.activitycalendar.data;

import com.activity.activitycalendar.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNameOrEmailAndPassword(String username,String email, String password);
    User findByUserName(String userName);

    @Query(value = "select u from User u where u.email = :name or u.userName = :name")
    User findByUserNameOrEmail(String name);

}
