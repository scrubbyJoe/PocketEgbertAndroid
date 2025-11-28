package com.example.pocketegbert;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {user.class}, version = 1)
public abstract class userDatabase extends RoomDatabase {
    public abstract userDAO userDAO();
}
