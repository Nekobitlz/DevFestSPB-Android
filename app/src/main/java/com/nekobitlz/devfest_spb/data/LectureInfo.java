package com.nekobitlz.devfest_spb.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Color;
import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.nekobitlz.devfest_spb.data.Tracks.*;
import static com.nekobitlz.devfest_spb.data.Tracks.COMMON;
import static com.nekobitlz.devfest_spb.data.Tracks.COMMON_NAME;

/*
    Class for storing lecture data
*/
@Entity
public class LectureInfo implements Serializable {

    @NonNull
    @PrimaryKey
    @SerializedName("title")
    private String title;
    @SerializedName("speaker")
    private String speakerId;
    @SerializedName("time")
    private String date;
    @SerializedName("room")
    private String address;
    @SerializedName("track")
    private String label;
    @SerializedName("description")
    private String description;

    /*
        Lecture initialization
    */
    public LectureInfo(String speakerId, String date, String address,
                       @NonNull String title, String label, String description) {
        this.speakerId = speakerId;
        this.date = date;
        this.address = address;
        this.title = title;
        this.label = label;

        //In the xml file for readability, we add a line break that we need to remove
        this.description = description.replaceAll("[\\s]{2,}", " ");
    }

    /*
        GETTERS
    */
    public String getSpeakerId() {
        return speakerId;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return "Room " + address;
    }

    public String getTitle() {
        return title;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public int getBackgroundColor() {
        switch (label.toLowerCase()) {
            case ANDROID_NAME : return Color.parseColor(ANDROID.getColor());
            case FRONTEND_NAME : return Color.parseColor(FRONTEND.getColor());
            case COMMON_NAME : return Color.parseColor(COMMON.getColor());
        }

        return 0;
    }
}
