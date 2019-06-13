package com.nekobitlz.devfest_spb.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.baseMvp.BaseActivity;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;
import com.nekobitlz.devfest_spb.mvp.MainContract;
import com.nekobitlz.devfest_spb.mvp.MainPresenter;

import java.util.ArrayList;

public class MainActivity extends BaseActivity<MainContract.View, MainContract.Presenter> implements MainContract.View {

    private final static String FRAGMENT_TAG = "speaker_list_fragment";

    private SpeakerListFragment speakerListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            speakerListFragment = SpeakerListFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, speakerListFragment, FRAGMENT_TAG)
                    .commit();
        } else {
            speakerListFragment = (SpeakerListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }
    }

    @Override
    public void loadInfo() {
        presenter.loadInfo();
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void setAdapter(ArrayList<SpeakerInfo> speakersInfo, ArrayList<LectureInfo> lecturesInfo) {
        speakerListFragment.setAdapter(speakersInfo, lecturesInfo);
    }

    public LectureInfo getLectureBySpeaker(SpeakerInfo currentSpeaker) {
        return presenter.getLectureBySpeaker(currentSpeaker);
    }
}