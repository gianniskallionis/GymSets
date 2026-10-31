package com.giannis.workout_tracker.controller;

import com.giannis.workout_tracker.config.JwtService;
import com.giannis.workout_tracker.dto.LoginRequest;
import com.giannis.workout_tracker.dto.LoginResponse;
import com.giannis.workout_tracker.dto.RegisterRequest;
import com.giannis.workout_tracker.dto.UserResponse;
import com.giannis.workout_tracker.model.User;
import com.giannis.workout_tracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;


    // the passwordEncoder and the userRepository are made with @bean, i had to write the passwordEncoder one
    // but the userRepository is made automatically since it extends JpaRepository
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody RegisterRequest request){
        Objects.requireNonNull(request.password(),"PROBLEM:password of user is null");
        if(request.password().isBlank()){
            throw new IllegalArgumentException("PROBLEM:password of user is blank");
        }
        if ( userRepository.findByUsername(  request.username()   )  .isPresent()     ){
            throw new IllegalArgumentException("User with this username Already exists");    }

            String hash= passwordEncoder.encode(request.password());// we  encode the password with a hash

            User currentUser=new User(request.username(), hash);

            currentUser=userRepository.save(currentUser); // by saving the User we give the object the id
            // now currentUser has an id . and we make a UserResponse object out of the currentUser

            UserResponse userResponse=new UserResponse(currentUser.getId(), currentUser.getUsername());

            return userResponse;
    }


    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request){
        Objects.requireNonNull(request.password(),"username or password is incorrect");
        if(request.password().isBlank()){
            throw new IllegalArgumentException("username or password is incorrect");
        }
        // we give a general exception message to prevent an attacker from figuring out which  usernames already exist
        // from the error code
        User currentUser=userRepository.findByUsername(request.username()).orElseThrow(()
                ->new IllegalArgumentException("username or password is incorrect"));
            if (passwordEncoder.matches( request.password(),currentUser.getPasswordHash() )){
                String token = jwtService.generateToken(currentUser.getUsername());
                return new LoginResponse(token);


            }else throw new IllegalArgumentException("username or password is incorrect");
    }









}
