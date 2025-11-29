package com.example.pocketegbert;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface gameDataDAO {

    @Insert
    void addGameData(gameData gd);

    @Update
    void updateGameData(gameData gd);

    @Delete
    void deleteGameData(gameData gd);

    @Query("SELECT * FROM gameData WHERE userId = :useId")
    gameData getData(int useId);
}
