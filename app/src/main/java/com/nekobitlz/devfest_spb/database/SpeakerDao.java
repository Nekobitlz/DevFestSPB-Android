package com.nekobitlz.devfest_spb.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;

import java.util.List;

@Dao
public interface SpeakerDao {

    @Query("SELECT * FROM SpeakerInfo")
    List<SpeakerInfo> getAll();

    @Insert
    void insertAll(SpeakerInfo... speakersInfo);

    @Query("DELETE FROM SpeakerInfo")
    void deleteAll();
}
