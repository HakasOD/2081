package com.exammple.eventmanager1.provider;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class, Event.class}, version = 1)
public abstract class EventManagerDatabase extends RoomDatabase{
    public static final  String EVENT_MANAGER_DATABASE_NAME = "eventManagerDatabase";
    public abstract EventManagerDAO EventManagerDAO();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile EventManagerDatabase INSTANCE;

    public static EventManagerDatabase getDatabase (final Context context){
        if(INSTANCE == null){
            synchronized (EventManagerDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EventManagerDatabase.class, EVENT_MANAGER_DATABASE_NAME).build();
                }
            }
        }

        return INSTANCE;
    }
}
