package com.popug.tasktracker.userservice.config;

import com.popug.tasktracker.userservice.entity.PopugUser;
import com.popug.tasktracker.userservice.repository.PopugUserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    @ConditionalOnProperty(name = "app.web.security", havingValue="false", matchIfMissing = false)
    public SecurityFilterChain permitAll(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll())
                .csrf(it -> it.disable()); // to support external restful requests
        return http.build();
    }

    @Bean
    @ConditionalOnProperty(name = "app.web.security", havingValue="true", matchIfMissing = true)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .csrf(it -> it.disable())
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PopugUserRepository popugUserRepository) {
        return (username) -> {
            PopugUser user = popugUserRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }
            return new User(username, user.getPassword(), Collections.emptyList());
        };
    }

    @Bean
    @Profile("dev")
    public PasswordEncoder passwordEncoderForDev() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Profile("!dev")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}