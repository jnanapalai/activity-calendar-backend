package com.activity.activitycalendar.configuration;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {

    private static final String SERVER_URL= "http://localhost:8180";
    private static final String REALM = "oauth2-realm-demo";
    private static final String CLIENT_ID = "oauth2-demo-client";
    private static final String USER_NAME = "oauth2-demo-user";
    private static final String PASSWORD = "test12345";

    @Bean
    public Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(CLIENT_ID)
                .username(USER_NAME)
                .password(PASSWORD)
                .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
                .build();
    }
}
