package com.nekobitlz.devfest_spb.network;

import com.google.gson.annotations.SerializedName;
import com.nekobitlz.devfest_spb.data.SpeakerInformation;

import java.util.ArrayList;

public class Speaker {
    @SerializedName("speakers")
    private ArrayList<SpeakerInformation> speakersList = new ArrayList<>();

    public ArrayList<SpeakerInformation> getSpeakersList() {
        return speakersList;
    }
}


