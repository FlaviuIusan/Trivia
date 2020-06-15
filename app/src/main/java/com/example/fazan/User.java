package com.example.fazan;

import java.io.Serializable;

public class User implements Serializable {

    public String username="";
    public long score=0;
    public String userId="";

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userName, long score, String userId){
        this.username = userName;
        this.score = score;
        this.userId = userId;
    }

    public User(User user){
        this.username = user.username;
        this.score = user.score;
        this.userId = user.userId;
    }

}
