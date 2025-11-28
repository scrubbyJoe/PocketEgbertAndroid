package com.example.pocketegbert;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class gameData {
    @PrimaryKey(autoGenerate = true)
    int id;
    int userId;
    int score;
    int happiness;


    public gameData(int score, int happiness, int userId){
        this.score=score;
        this.happiness=happiness;
        this.userId=userId;
    }

    //getters and setters
    public void setScore(int score){
        this.score=score;
    }

    public int getScore(){
        return score;
    }

    public void setHappiness(int happiness){
        this.happiness=happiness;
    }

    public int getHappiness(){
        return happiness;
    }

}
