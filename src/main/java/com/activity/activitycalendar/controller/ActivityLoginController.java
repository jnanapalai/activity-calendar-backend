package com.activity.activitycalendar.controller;

import lombok.RequiredArgsConstructor;

import com.activity.activitycalendar.security.CustomUserDetails;
import com.activity.activitycalendar.security.CustomUserDetailsService;
import com.activity.activitycalendar.security.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ActivityLoginController {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

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
