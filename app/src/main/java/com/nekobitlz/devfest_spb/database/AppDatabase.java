package com.nekobitlz.devfest_spb.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;

@Database(entities = { SpeakerInfo.class, LectureInfo.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "SpeakerDb.db";

    public abstract LectureDao lectureDao();
    public abstract SpeakerDao speakerDao();
}
