package com.easybytes.easyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                        .requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/","/home").permitAll()
                        .requestMatchers("/holidays/**").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/login").permitAll()
                )
                .formLogin(formLogin->formLogin.loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout->logout.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
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
    /*
    Example 29

    refer to link:
    https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html#page-title
    */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user")
                .password("12345")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("admin")
                .password("54321")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
