package com.example.pocketegbert;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface userGameDataDAO {
    @Transaction
    @Query("SELECT * FROM user")
    public List<userGameData> getAllUsesGameData();

    @Transaction
    @Query(("SELECT * FROM user WHERE username = :username"))
    userGameData getGameData(String username);

}
