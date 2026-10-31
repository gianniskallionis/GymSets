package com.giannis.workout_tracker.controller;

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


    // the passwordEncoder and the userRepository are made with @bean, i had to write the passwordEncoder one
    // but the userRepository is made automatically since it extends JpaRepository
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;

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


}
