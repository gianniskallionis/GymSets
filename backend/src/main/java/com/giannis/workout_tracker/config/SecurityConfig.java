package com.giannis.workout_tracker.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    @Bean  // with @Bean, we make a new PasswordEncoder object and spring keeps it .
    public BCryptPasswordEncoder PasswordEncoder(){
            return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http,JwtAuthFilter jwtAuthFilter) throws Exception{
        http.csrf(csrf -> csrf.disable());  // first we close the csrf  so post and delete points dont return 403
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()); // we let requests that match the pattern in
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);//we add the filter before spring check
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// makes spring statless( forgetfull)
        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        // requests without tokens throw a  401 , not a 403


        return http.build(); // we return the settings to spring



    }




    public SecurityConfig() {
    }


}
