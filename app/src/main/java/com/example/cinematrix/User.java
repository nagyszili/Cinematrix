package com.example.cinematrix;

import com.example.cinematrix.api.MovieResult;

import java.util.HashMap;

public class User {

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private HashMap<String, MovieResult> favoriteMovies;


    public User() {
    }

    public HashMap<String, MovieResult> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(HashMap<String, MovieResult> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
