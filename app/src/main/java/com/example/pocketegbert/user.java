package com.example.pocketegbert;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class user {

    @PrimaryKey(autoGenerate = true)
    int id;
    String username;

    public user(String username){
        this.username=username;
    }

    //getters and setters
    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername(){
        return username;
    }

}
