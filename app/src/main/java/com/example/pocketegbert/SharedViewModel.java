package com.example.pocketegbert;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel{

    private userGameData currentPlayer;
    private int currentSave;

    public void setPlayersGameData(userGameData data){
        this.currentPlayer = data;
    }

    public  userGameData getPlayersGameData(){
        return currentPlayer;
    }

    public void setSaveSlot(int saveSlot){
        this.currentSave=saveSlot;
    }

    public int getSaveSlot(){
        return currentSave;
    }

    public boolean isThereAPlayer(){
        return currentPlayer != null;
    }

}
