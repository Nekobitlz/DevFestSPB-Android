package com.nekobitlz.devfest_spb.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
    Class for storing speaker data
*/
public class SpeakerInformation implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("photo")
    private String image;
    @SerializedName("jobTitle")
    private String jobTitle;
    @SerializedName("company")
    private String company;
    @SerializedName("location")
    private String location;
    @SerializedName("about")
    private String about;
    @SerializedName("flagImage")
    private String flagImage;
    @SerializedName("links")
    private Links links;

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

    public Links getLinks() {
        return links;
    }

    public class Links implements Serializable {
        @SerializedName("twitter")
        private String twitter;
        @SerializedName("telegram")
        private String telegram;
        @SerializedName("github")
        private String github;

        public String getTwitter() {
            return twitter;
        }

        public String getTelegram() {
            return telegram;
        }

        public String getGithub() {
            return github;
        }
    }
}
