package com.nekobitlz.devfest_spb;

/*
    Class for storing lecture data
*/
public class LectureInformation {
    private String speakerName, date, address, title, label, description;

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
