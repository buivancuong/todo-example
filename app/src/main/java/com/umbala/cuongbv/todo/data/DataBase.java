package com.umbala.cuongbv.todo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.umbala.cuongbv.todo.model.Task;

import static com.umbala.cuongbv.todo.data.DataBase.DATABASE_VERSION;

@Database(entities = {Task.class}, version = DATABASE_VERSION)
public abstract class DataBase extends RoomDatabase {

    public static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Room-database";
    public abstract TaskDAO taskDAO();

    private static DataBase instance;

    public static DataBase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, DataBase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
