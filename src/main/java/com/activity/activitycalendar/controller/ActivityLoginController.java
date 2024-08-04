package com.activity.activitycalendar.controller;

import com.activity.activitycalendar.security.CustomUserDetailsService;
import com.activity.activitycalendar.security.JwtTokenUtil;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import lombok.RequiredArgsConstructor;

/*
This class is responsible for authenticate user when
com.activity.authorization.type=custom. For any other
value this class will not activate or function.
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin
@ConditionalOnProperty(name="com.activity.authorization.type", havingValue = "custom")
public class ActivityLoginController {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

    /**
     * This method responsible for validate the user by username and password.
     *
     * @param credential instance of Map having username and password
     * @return
     * @throws Exception throws exception if invalid
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody Map<String,String> credential) throws Exception {
        System.out.println("Inside Authentication Filter");
        String userName = credential.get("username");
        String password = credential.get("password");
        authenticationConfiguration.
                getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        final String token = jwtTokenUtil.generateJwtToken(userDetails);
        return ResponseEntity.ok(token);
    }
}
