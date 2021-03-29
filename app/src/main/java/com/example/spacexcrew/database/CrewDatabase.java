package com.example.spacexcrew.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.spacexcrew.dataClasses.Crew;

@Database(entities = {Crew.class}, version = 1)
public abstract class CrewDatabase extends RoomDatabase {
    public static final String DB_NAME = "CrewDatabase";
    private static CrewDatabase INSTANCE;

    public static CrewDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CrewDatabase.class) {
                if (INSTANCE == null) {
                    CrewDatabase instance = Room.databaseBuilder(context, CrewDatabase.class, DB_NAME).build();
                    INSTANCE = instance;
                    return instance;
                }
            }
        }
        return INSTANCE;
    }

    public abstract CrewDao crewDao();
}
