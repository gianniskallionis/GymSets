package com.giannis.workout_tracker.repository;

import com.giannis.workout_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User> findByUsername(String username);
   // there are select words that you can use and make methods that are automatically made from Spring
    // such words are Find+name of a field ex: findByDate, findByUsername , etc.
    //  existsByUsername, existsByDate, returns T/F
    // countBy, deleteBy, etc
    boolean existsByUsername(String username);




}
