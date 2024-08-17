package com.activity.activitycalendar.service;

import lombok.RequiredArgsConstructor;

import com.activity.activitycalendar.data.UserRepository;
import com.activity.activitycalendar.entity.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User createUser(User user) {
     return this.userRepository.save(user);
    }

    public Optional<User> checkUserExist(String userNameOrEmail, String password) {
       return this.userRepository.findByUserNameOrEmailAndPassword(userNameOrEmail,userNameOrEmail,password);
    }

    public User getUserByUserNameOrEmail(String userNameOrEmail) {
        return  this.userRepository.findByUserNameOrEmail(userNameOrEmail);
    }
}
