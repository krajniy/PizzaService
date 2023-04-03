package com.telran.pizzaservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security.
 *
 * @author Elena Ivanishcheva
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * Configures authentication manager to use in-memory authentication.
     *
     * @param auth the AuthenticationManagerBuilder instance to configure
     * @throws Exception if an error occurs while configuring the authentication manager
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("ADMIN");
    }

    /**
     * Configures the security filter chain with authorization rules for different endpoints.
     *
     * @param http the HttpSecurity instance to configure
     * @return the SecurityFilterChain instance created by this method
     * @throws Exception if an error occurs while configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/guest/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )

                .httpBasic();
        http.headers().frameOptions().disable();

        return http.build();
    }


}