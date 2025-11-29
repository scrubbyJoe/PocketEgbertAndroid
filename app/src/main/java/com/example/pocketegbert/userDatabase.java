package com.example.pocketegbert;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {user.class, gameData.class}, version = 2)
public abstract class userDatabase extends RoomDatabase {
    public abstract userDAO userDAO();

    public abstract gameDataDAO gameDataDAO();

    public abstract userGameDataDAO userGameDataDAO();
}
