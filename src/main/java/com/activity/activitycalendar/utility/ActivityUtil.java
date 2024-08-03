package com.activity.activitycalendar.utility;

import com.activity.activitycalendar.security.CustomUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class ActivityUtil {


    String userName = null;

    public String getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) principal;
                userName = customUserDetails.getUsername();
            } if (principal instanceof Jwt) {
                Jwt jwt = (Jwt) principal;
                userName = jwt.getClaims().get("preferred_username").toString();
            }
        }
        return userName;
    }

}


