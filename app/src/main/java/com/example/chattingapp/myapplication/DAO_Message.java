package com.example.chattingapp.myapplication;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import java.util.List;

@Dao
public interface DAO_Message {

    @Query("select * from messages order by  timeStamp asc")
    public List<Entity_Message>getAllMessage();
    @Insert
    public void insertMessage(Entity_Message entity_message);
    @Delete
    public void deleteMessage(Entity_Message entity_message);

}
