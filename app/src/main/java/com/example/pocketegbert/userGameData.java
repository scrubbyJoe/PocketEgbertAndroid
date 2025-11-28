package com.example.pocketegbert;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class userGameData {
    @Embedded
    public user user;

    @Relation(
            parentColumn = "id",
            entityColumn = "userId"

    )
    public List<gameData> usersGameData;
}
