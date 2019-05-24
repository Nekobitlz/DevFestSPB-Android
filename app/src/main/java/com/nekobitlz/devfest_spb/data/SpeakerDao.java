package com.nekobitlz.devfest_spb.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SpeakerDao {

    @Query("SELECT * FROM SpeakerInfo")
    List<SpeakerInfo> getAll();

    @Insert
    void insertAll(SpeakerInfo... speakersInfo);

    @Delete
    void delete(SpeakerInfo speakerInfo);

    @Query("DELETE FROM SpeakerInfo")
    void deleteAll();

    @Query("SELECT * FROM SpeakerInfo WHERE id LIKE :id LIMIT 1")
    SpeakerInfo findById(String id);
}
