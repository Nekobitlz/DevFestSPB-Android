package com.nekobitlz.devfest_spb.network;

import com.google.gson.annotations.SerializedName;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;

import java.util.ArrayList;

public class ApiData {

    @SerializedName("speakers")
    private ArrayList<SpeakerInfo> speakersList = new ArrayList<>();

    @SerializedName("schedule")
    private Schedule schedule;

    public ArrayList<SpeakerInfo> getSpeakersList() {
        return speakersList;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public class Schedule {
        @SerializedName("talks")
        private ArrayList<LectureInfo> lecturesList = new ArrayList<>();

        @SerializedName("activities")
        private ArrayList<Activities> activitiesList = new ArrayList<>();

        public ArrayList<LectureInfo> getLecturesList() {
            return lecturesList;
        }

        public ArrayList<Activities> getActivitiesList() {
            return activitiesList;
        }

        private class Activities {
            @SerializedName("title")
            private String title;
            @SerializedName("time")
            private String time;

            public String getTitle() {
                return title;
            }

            public String getTime() {
                return time;
            }
        }
    }
}
