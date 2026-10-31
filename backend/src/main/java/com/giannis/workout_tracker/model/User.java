package com.giannis.workout_tracker.model;


import jakarta.persistence.*;

import java.util.Objects;

@Table(name="app_user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String username;
    private String passwordHash;



    protected User() {
    }

    public User(String username, String passwordHash){

        setUsername(username);
        setPasswordHash(passwordHash);
    }


    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        Objects.requireNonNull(passwordHash,"PROBLEM:password Hash is null");
        if(passwordHash.isBlank()){
            throw new IllegalArgumentException("PROBLEM:Blank password Hash");
        }
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Objects.requireNonNull(username,"PROBLEM:username of user is null");
        if(username.isBlank()){
            throw new IllegalArgumentException("PROBLEM:Blank usernamename");
        }
        this.username = username;
    }

    public Long getId(){
        return id;
    }
}
