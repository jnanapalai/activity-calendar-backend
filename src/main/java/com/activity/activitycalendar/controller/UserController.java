package com.activity.activitycalendar.controller;

import lombok.RequiredArgsConstructor;

import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.mapper.UserMapper;
import com.activity.activitycalendar.model.UserDto;
import com.activity.activitycalendar.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = UserMapper.userMapper.mapUserDtoToUser(userDto);
        System.out.println("User information is" + user.getFirstName());
        return ResponseEntity.status(201).body(this.userService.createUser(user));
    }

//    @GetMapping("/validateuser")
//    public ResponseEntity checkUserExist() {
////        if(this.userService.checkUserExist(userNameorEmail,password).isPresent()) {
////            return ResponseEntity.ok().build();
////        } else {
////            return ResponseEntity.status(404).build();
////        }
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/validateuser")
    public ResponseEntity checkUserExist(@RequestParam Map<String, String> requestParams) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/returnvalidateuser")
    public ResponseEntity checkUserExist() {
        return ResponseEntity.ok().build();
    }


}
