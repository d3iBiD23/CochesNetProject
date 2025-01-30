package com.example.cochesnetproject.ui.home;

import androidx.room.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ElementoCoche.class}, version = 1)
public abstract class ElementoCocheDatabase extends RoomDatabase {
    public abstract ElementoCocheDao cocheDao();

    private static volatile ElementoCocheDatabase INSTANCE;

    public static ElementoCocheDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ElementoCocheDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ElementoCocheDatabase.class, "coches_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}