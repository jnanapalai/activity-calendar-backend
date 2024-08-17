package com.activity.activitycalendar.controller;

import com.activity.activitycalendar.entity.User;
import com.activity.activitycalendar.mapper.UserMapper;
import com.activity.activitycalendar.model.UserDto;
import com.activity.activitycalendar.service.KeycloakService;
import com.activity.activitycalendar.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
/**
 * This class is responsible for create user while signup.
 */
public class UserController {

    private final UserService userService;
    private KeycloakService keycloakService;


    @Autowired(required = false)
    public void setDependency(KeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @Value("${com.activity.authorization.type}")
    private String authorizationType;

    /**
     * This method is responsible to create user. In addition to
     * creating user in database it creates user in keycloak if
     * configuration parameter com.activity.authorization.type = oauth
     *
     * @param userDto instance of UserDto
     * @return instance of ResponseEntity with created user
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = UserMapper.userMapper.mapUserDtoToUser(userDto);
        if (authorizationType.equals("oauth")) {
            System.out.println("Creating Keycloak User...");
            this.keycloakService.createUserForKeycloak(userDto);
        }
        return ResponseEntity.status(201).body(this.userService.createUser(user));
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = this.userService.getAllUsers();
        List<UserDto> userDtoList = UserMapper.userMapper.mapUserListToDtoList(users);
        return  ResponseEntity.ok(userDtoList);
    }
}
