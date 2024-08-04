package com.activity.activitycalendar.configuration;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is for configuring keycloak
 * It will only activate if application run with
 * configuration parameter com.activity.authorization.type = oauth
 */
@Configuration
@ConditionalOnProperty(name = "com.activity.authorization.type", havingValue = "oauth")
public class KeycloakConfiguration {

    private static final String SERVER_URL= "http://localhost:8180";
    private static final String REALM = "oauth2-realm-demo";
    private static final String CLIENT_ID = "oauth2-demo-client";
    private static final String USER_NAME = "oauth2-demo-user";
    private static final String PASSWORD = "test12345";

    /**
     * This method create Keycloak Instance with all
     * configuration like realm,client id, keycloak server url
     *
     * @return return Instance of Keycloak
     */
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
