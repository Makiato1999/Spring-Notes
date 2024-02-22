package com.easybytes.easyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// from Spring Security 6.1 and Spring Boot 3.1.0 versions,
// the Spring Security framework team recommends using the Lambda DSL style
// for configuring security for APIs, web paths, etc.
@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /*
        Example 28

        /*
        /home
        /holidays
        /contact
        /saveMsg
        /courses
        /about
         */
        // We can disable the same for now and enable it ni the coming sections when we started generating CSRF tokens.
        http.csrf((csrf)->csrf.disable()).authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/","/home").permitAll()
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        /**
         * Example 27

        // permit all requests inside the web application
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        // deny all requests inside the web application
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest().denyAll())// HTTP ERROR 403
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
         */

        return http.build();
    }
}
