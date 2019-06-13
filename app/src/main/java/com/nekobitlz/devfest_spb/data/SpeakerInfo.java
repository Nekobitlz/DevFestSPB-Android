package com.nekobitlz.devfest_spb.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Class for storing speaker data
*/
@Entity
public class SpeakerInfo implements Serializable {

    @NonNull
    @PrimaryKey
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
    @TypeConverters({ LinksConverter.class })
    private Links links;

    /*
        Speaker initialization
    */
    public SpeakerInfo(@NonNull String id, String firstName, String lastName, String image, String jobTitle,
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
        return company.isEmpty() ? "" : " at " + company;
    }

    public String getFlagImage() {
        return "flag_" + flagImage;
    }

    public Links getLinks() {
        return links;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getPosition() {
        return jobTitle + getCompany();
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public static class Links implements Serializable {
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

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public void setTelegram(String telegram) {
            this.telegram = telegram;
        }

        public void setGithub(String github) {
            this.github = github;
        }
    }

    public static class LinksConverter {
        @TypeConverter
        public String fromLinks(Links links) {
            return links.getGithub() + " " + links.getTelegram() + " " + links.getTwitter();
        }

        @TypeConverter
        public Links toLinks(String linksString) {
            List<String> linksList = Arrays.asList(linksString.split(" "));
            Links links = new Links();

            links.setGithub(linksList.get(0));
            links.setTelegram(linksList.get(1));
            links.setTwitter(linksList.get(2));

            return links;
        }
    }
}
