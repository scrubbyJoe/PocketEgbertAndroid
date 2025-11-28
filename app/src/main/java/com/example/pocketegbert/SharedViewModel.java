package com.example.pocketegbert;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel{

    private userGameData currentPlayer;

    public void setPlayersGameData(userGameData data){
        this.currentPlayer = data;
    }

    public  userGameData getPlayersGameData(){
        return currentPlayer;
    }

    public boolean isThereAPlayer(){
        return currentPlayer != null;
    }

}
