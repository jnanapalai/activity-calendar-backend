package com.activity.activitycalendar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(Long userId, String firstName,
                      String lastName, String userName,
                      String email, String password,
                      String accountType, @JsonProperty("company") String organisationName) {
}
