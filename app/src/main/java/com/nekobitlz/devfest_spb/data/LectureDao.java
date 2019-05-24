package com.nekobitlz.devfest_spb.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LectureDao {

    @Query("SELECT * FROM lectureinfo")
    List<LectureInfo> getAll();

    @Insert
    void insertAll(LectureInfo... lecturesInfo);

    @Delete
    void delete(LectureInfo lectureInfo);

    @Query("DELETE FROM lectureinfo")
    void deleteAll();

    @Query("SELECT * FROM lectureinfo WHERE title LIKE :title LIMIT 1")
    LectureInfo findByName(String title);

    @Query("SELECT * FROM lectureinfo WHERE speakerId LIKE :speakerId")
    List<LectureInfo> findBySpeakerId(String speakerId);
}
