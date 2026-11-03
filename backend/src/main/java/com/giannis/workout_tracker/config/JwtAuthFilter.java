package com.giannis.workout_tracker.config;

import com.giannis.workout_tracker.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JwtAuthFilter Verifies that the token of the request is valid  and notifies spring who sent the request
 * its like a bouncer in a club
 *
 *
 *
 * @author giannis
 * @version 01-Nov-26
 * @since 2026
 */
@Component  /* Component creates an instance of the class ( an object- a bean) and it provides the bean to
any other class that needs it automatically without me writing code
*/
public class JwtAuthFilter extends OncePerRequestFilter {
    JwtService jwtService;
    UserRepository userRepository;



    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }

    /**
     * receives the request, response alongisde the filter chain and verifies the token and finds out who sent the request
     * @param request the request of the client
     * @param response the response towards the client
     * @param filterChain the filterChain is responsible to forward the request to the correct Class after it has checked it
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header= request.getHeader("Authorization");  // the name of the header is Authorization
        // header the metadata of the token
        if (header!=null && header.startsWith("Bearer ")){
            String token=header.substring(7);// we cut-off the first 7 letters of the header(Bearer ) and we
            // save the token
           try {
               String username= jwtService.extractUsername(token);
               if( userRepository.existsByUsername(username)  ){ // if the username  exists
                   var auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
                   SecurityContextHolder.getContext().setAuthentication(auth);}


           } catch(Exception e){System.out.println("JWT ERROR: " + e.getMessage());};



        }
        filterChain.doFilter(request,response);

    }


}
