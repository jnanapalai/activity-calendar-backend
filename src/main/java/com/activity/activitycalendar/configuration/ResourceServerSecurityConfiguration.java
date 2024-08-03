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

@Configuration
@EnableWebSecurity(debug = true)
@ConditionalOnProperty(name="com.activity.resourceserver.conf.enabled", havingValue = "true")
public class ResourceServerSecurityConfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        System.out.println("Inside Security Filter chain group of ResourceServerSecurityConfiguration");
        httpSecurity
                .authorizeRequests().requestMatchers("/user","/authenticate", "/error", "/h2-console")
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**")
                ,new AntPathRequestMatcher("*.css"), new AntPathRequestMatcher("*.png"), new AntPathRequestMatcher("*.js"));
    }
}
