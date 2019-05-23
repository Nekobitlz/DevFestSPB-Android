package com.nekobitlz.devfest_spb.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
    Class for storing lecture data
*/
public class LectureInfo implements Serializable {

    @SerializedName("speaker")
    private String speakerId;
    @SerializedName("time")
    private String date;
    @SerializedName("room")
    private String address;
    @SerializedName("title")
    private String title;
    @SerializedName("track")
    private String label;
    @SerializedName("description")
    private String description;

    /*
        Lecture initialization
    */
    public LectureInfo(String speakerId, String date, String address,
                       String title, String label, String description) {
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
        return address;
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
}
