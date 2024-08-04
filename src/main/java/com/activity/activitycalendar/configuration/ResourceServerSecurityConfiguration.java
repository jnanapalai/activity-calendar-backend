package com.activity.activitycalendar.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * This class all security oriented configuration for
 * Activity Calendar. It will activate if application run
 * with com.activity.authorization.type = oauth.
 */
@Configuration
@EnableWebSecurity(debug = true)
@ConditionalOnProperty(name="com.activity.authorization.type", havingValue = "oauth")
public class ResourceServerSecurityConfiguration {

    /**
     * This method create Security Filter chain
     *
     * @param httpSecurity instance of HttpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        System.out.println("Inside Security Filter chain group of ResourceServerSecurityConfiguration");
        httpSecurity
                .authorizeRequests().requestMatchers(
                        "/user","/authenticate", "/error", "/h2-console",
                        "/swagger-ui/*", "/v3/api-docs","/v3/api-docs/*","/v3/**", "/swagger-ui/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .oauth2ResourceServer(
                        httpSecurityOAuth2ResourceServerConfigurer
                -> httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));

        return httpSecurity.build();
    }

    /**
     * Method to ignore security for specific Endpoints lik swagger and h2 database
     * @return instance of WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("v3/api-docs"),
                new AntPathRequestMatcher("/v3/api-docs/**")
                ,new AntPathRequestMatcher("*.css"), new AntPathRequestMatcher("*.png"), new AntPathRequestMatcher("*.js"));
    }
}
