package com.nekobitlz.devfest_spb.data;

import java.io.Serializable;

/*
    Class for storing speaker data
*/
public class SpeakerInformation implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String image;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private String flagImage;

    /*
        Speaker initialization
    */
    public SpeakerInformation(String id, String firstName, String lastName, String image, String jobTitle,
                              String company, String location, String about, String flagImage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.jobTitle = jobTitle;
        this.company = company;
        this.location = location;
        this.flagImage = flagImage;

        //In the xml file for readability, we add a line break that we need to remove
        this.about = about.replaceAll("[\\s]{2,}", " ");
    }

    /*
        GETTERS
    */
    public String getFirstName() {
        return firstName;
    }

    public String getImage() {
        return image;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public String getAbout() {
        return about;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getFlagImage() {
        return flagImage;
    }
}
