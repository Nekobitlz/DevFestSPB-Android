package com.nekobitlz.devfest_spb.database;

import android.app.Application;
import android.arch.persistence.room.Room;

import static com.nekobitlz.devfest_spb.database.AppDatabase.DATABASE_NAME;

public class App extends Application {

    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
