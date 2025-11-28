package com.example.pocketegbert;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface userDAO {
    @Insert
    void addUser(user u);
    @Update
    void updateUser(user u);
    @Delete
    void deleteUser(user u);

    @Query("SELECT * FROM user Where id = :id")
    user getUserId(int id);

    @Query("SELECT * FROM user Where username = :username")
    user getUsername(String username);

    @Query("SELECT * FROM user")
    List<user> getAllUsers();
}
