package com.giannis.workout_tracker.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean  // with @Bean, we make a new PasswordEncoder object and spring keeps it .
    public BCryptPasswordEncoder PasswordEncoder(){
            return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable());  // first we close the csrf  so post and delete points dont return 403
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // we let Everyone in
        return http.build(); // we return the settings to spring



    }




    public SecurityConfig() {
    }


}
