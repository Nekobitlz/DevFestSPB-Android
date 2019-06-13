package com.nekobitlz.devfest_spb.mvp;

import com.nekobitlz.devfest_spb.baseMvp.BasePresenter;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;

import java.util.ArrayList;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainContract.View view;
    private MainContract.Model model;

    private ArrayList<LectureInfo> lecturesInfo;
    private ArrayList<SpeakerInfo> speakersInfo;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        model = new MainModel();
    }

    @Override
    public void loadInfo() {
        model.loadInfo();
        speakersInfo = model.getSpeakersInfo();
        lecturesInfo = model.getLecturesInfo();
        view.setAdapter(speakersInfo, lecturesInfo);
    }

    @Override
    public LectureInfo getLectureBySpeaker(SpeakerInfo speakerInfo) {
        for (LectureInfo lecture : lecturesInfo) {
            if (lecture.getSpeakerId().equals(speakerInfo.getId())) {
                return lecture;
            }
        }

        return null;
    }
}
