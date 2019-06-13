package com.nekobitlz.devfest_spb.mvp;

import com.nekobitlz.devfest_spb.baseMvp.BaseContact;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;

import java.util.ArrayList;

public class MainContract {

    public interface View extends BaseContact.View {
        void loadInfo();
        void setAdapter(ArrayList<SpeakerInfo> speakersInfo, ArrayList<LectureInfo> lecturesInfo);
    }

    public interface Presenter extends BaseContact.Presenter<MainContract.View> {
        void loadInfo();
        LectureInfo getLectureBySpeaker(SpeakerInfo speakerInfo);
    }

    public interface Model {
        void loadInfo();
        ArrayList<SpeakerInfo> getSpeakersInfo();
        ArrayList<LectureInfo> getLecturesInfo();
    }
}
