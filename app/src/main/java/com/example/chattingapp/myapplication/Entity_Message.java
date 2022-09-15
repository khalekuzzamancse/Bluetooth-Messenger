package com.example.chattingapp.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class Entity_Message {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "sender")
    public String sender;
    @ColumnInfo(name = "receiver")
    public  String receiver;
    @ColumnInfo(name = "message")
    public  String message;
    @ColumnInfo(name = "timeStamp")
    public String timeStamp;


    public Entity_Message(String sender, String receiver, String message,String timeStamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timeStamp=timeStamp;
    }
    @Ignore
    public Entity_Message(int id,String sender, String receiver, String message) {
        this.id=id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }
}
