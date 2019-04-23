package com.nekobitlz.devfest_spb.data;

import java.io.Serializable;

/*
    Class for storing speaker data
*/
public class SpeakerInformation implements Serializable {
    private String name;
    private String image;
    private String position;
    private String location;
    private String speakerDescription;
    private String lecture;

    /*
        Speaker initialization
    */
    public SpeakerInformation(String name, String image, String position,
                              String location, String speakerDescription, String lecture) {
        this.name = name;
        this.image = image;
        this.position = position;
        this.location = location;
        this.lecture = lecture;

        //In the xml file for readability, we add a line break that we need to remove
        this.speakerDescription = speakerDescription.replaceAll("[\\s]{2,}", " ");
    }

    /*
        GETTERS
    */
    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPosition() {
        return position;
    }

    public String getLocation() {
        return location;
    }

    public String getSpeakerDescription() {
        return speakerDescription;
    }

    public String getLecture() {
        return lecture;
    }
}
