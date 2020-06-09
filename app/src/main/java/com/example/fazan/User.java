package com.example.fazan;

public class User {

    public String name;
    public int score;

    public User(String name, int score){
        this.name = name;
        this.score = score;
    }

    public User(User user){
        this.name = user.name;
        this.score = user.score;
    }
}
