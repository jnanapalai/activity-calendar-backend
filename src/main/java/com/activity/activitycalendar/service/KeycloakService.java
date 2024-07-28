package com.activity.activitycalendar.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import com.activity.activitycalendar.model.UserDto;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;
    private static final String REALM = "oauth2-realm-demo";
    private static final String REALM_ROLE = "oauth2-realm-role";

    public void createUserForKeycloak(UserDto userDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDto.getUserName());
        userRepresentation.setFirstName(userDto.getFirstName());
        userRepresentation.setLastName(userDto.getLastName());
        userRepresentation.setEmail(userDto.getEmail());
        userRepresentation.setEnabled(true);

        Response response = keycloak.realm(REALM).users().create(userRepresentation);
        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType("password");
            credentialRepresentation.setValue(userDto.getPassword());
            UserResource userResource = keycloak.realm(REALM).users().get(userId);
            userResource.resetPassword(credentialRepresentation);

        }
    }
}

