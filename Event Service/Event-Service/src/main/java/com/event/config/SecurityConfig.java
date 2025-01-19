package com.event.config;

import com.event.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.POST, "/event").hasRole("ADMIN")// General access
                            .requestMatchers(HttpMethod.GET, "/event/**").hasAnyAuthority("ROLE_ADMIN","ROLE_NORMAL")
                            .requestMatchers(HttpMethod.DELETE, "/event/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/event/**").hasAnyAuthority("ROLE_ADMIN","ROLE_NORMAL")
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
