package com.nekobitlz.devfest_spb;

import java.io.Serializable;

/*
    Class for storing lecture data
*/
public class LectureInformation implements Serializable {
    private String speakerName;
    private String date;
    private String address;
    private String title;
    private String label;
    private String description;

    /*
        Lecture initialization
    */
    public LectureInformation(String speakerName, String date, String address, String title, String label, String description) {
        this.speakerName = speakerName;
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
    public String getSpeakerName() {
        return speakerName;
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
