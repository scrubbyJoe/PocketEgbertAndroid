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
    int difficulty;
    int background;

    public gameData(int score, int happiness, int userId, int difficulty, int background){
        this.score=score;
        this.happiness=happiness;
        this.userId=userId;
        this.difficulty=difficulty;
        this.background=background;
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

    public void setDifficulty(int difficulty){
        this.difficulty=difficulty;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public void setBackground(int background){
        this.background=background;
    }

    public int getBackground(){
        return background;
    }
}
