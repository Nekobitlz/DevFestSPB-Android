package com.nekobitlz.devfest_spb.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = { SpeakerInfo.class, LectureInfo.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "SpeakerDb.db";

    public abstract LectureDao lectureDao();
    public abstract SpeakerDao speakerDao();
}
