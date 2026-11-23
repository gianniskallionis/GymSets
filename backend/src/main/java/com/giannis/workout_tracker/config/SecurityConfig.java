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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {


    @Bean  // with @Bean, we make a new PasswordEncoder object and spring keeps it .
    public BCryptPasswordEncoder PasswordEncoder(){
            return new BCryptPasswordEncoder();
    }

    /**
     * cors are the rules that state who is allowed to communicate with the backend, and the ways in which he can
     * @return source, the object that has the rules that we stated with the config
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config= new CorsConfiguration(); // corsConfiguration is the rules that state who  are allowed to talk to the backend
        config.setAllowedOrigins(List.of("http://localhost:5173") ); // who is allowed to talk to the backend
        config.setAllowedMethods(List.of("GET", "POST" ,"DELETE", "OPTIONS")    ); // what is he allowed to do
        config.setAllowedHeaders(List.of("Content-Type", "Authorization"  )   ); // which headers are allowed
        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);  // the config with the 3 rules   stated above work for all endpoints
        return source;


    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http,JwtAuthFilter jwtAuthFilter) throws Exception{
        http.cors(cors-> {}         );  // we activate the cors in the spring security
        http.csrf(csrf -> csrf.disable());  // first we close the csrf  so post and delete points dont return 403
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**","/error").permitAll()
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
