package com.nekobitlz.devfest_spb.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.nekobitlz.devfest_spb.data.LectureInfo;

import java.util.List;

@Dao
public interface LectureDao {

    @Query("SELECT * FROM lectureinfo")
    List<LectureInfo> getAll();

    @Insert
    void insertAll(LectureInfo... lecturesInfo);

    @Query("DELETE FROM lectureinfo")
    void deleteAll();
}
