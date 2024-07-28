package com.activity.activitycalendar.configuration;

import com.activity.activitycalendar.security.CustomAccessDeniedHandler;
import com.activity.activitycalendar.security.CustomAuthenticationEntryPoint;
import com.activity.activitycalendar.security.CustomUserDetailsService;
import com.activity.activitycalendar.security.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@ConditionalOnProperty(name="com.activity.resourceserver.conf.enabled", havingValue = "false")
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        System.out.println("Inside Security Filter Chain Group");

        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());
        /**
         * Uncomment below code if Secure connection using https you want
         *
         * httpSecurity.requiresChannel(channelRequestMatcherRegistry -> {
         *             channelRequestMatcherRegistry.anyRequest().requiresSecure();
         *                 });
         */


        httpSecurity
                .authorizeRequests().requestMatchers("/user","/authenticate", "/error").permitAll()
                .anyRequest().authenticated();
//                .and()
//                //basic authentication code
//                .httpBasic(httpSecurityHttpBasicConfigurer -> {
//                    httpSecurityHttpBasicConfigurer.authenticationEntryPoint(new AuthenticationEntryPoint() {
//                        @Override
//                        public void commence(HttpServletRequest request, HttpServletResponse response,
//                                             AuthenticationException authException) throws IOException, ServletException {
//                            response.sendError(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
//                        }
//                    });
//                })
//                //form based authentication
//                .formLogin(httpSecurityFormLoginConfigurer -> {
//                    httpSecurityFormLoginConfigurer
//                            .loginPage("http://localhost:4200")
//                            .loginProcessingUrl("/validateuser")
//                            .successForwardUrl("/returnvalidateuser")
//                            .usernameParameter("username")
//                            .passwordParameter("password")
//                            .permitAll();
//                });

        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//        httpSecurity.exceptionHandling(exception -> exception.accessDeniedHandler(new CustomAccessDeniedHandler())
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return httpSecurity.build();
    }

    private AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                    throws IOException, ServletException {
//                response.getWriter().append("OK");
//                response.setStatus(200);
                //response.sendRedirect(request.getHeader("referer"));

            }


        };
    }

    private AuthenticationFailureHandler failureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                    throws IOException, ServletException {
                response.getWriter().append("Authentication failure");
                response.setStatus(401);
            }
        };
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**")
                ,new AntPathRequestMatcher("*.css"), new AntPathRequestMatcher("*.png"), new AntPathRequestMatcher("*.js"));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

    }
}
