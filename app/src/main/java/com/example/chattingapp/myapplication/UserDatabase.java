package com.example.chattingapp.myapplication;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Entity_Message.class, exportSchema = false, version = 2)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME = "UserInfo";
    private static UserDatabase instance;

    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, UserDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public  abstract DAO_Message MessageDAO();
}
