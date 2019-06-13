package com.nekobitlz.devfest_spb.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.adapters.SpeakerRecyclerViewAdapter;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;

import java.util.ArrayList;

public class SpeakerListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final static String FRAGMENT_TAG = "speaker_fragment";

    private RecyclerView speakersRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SpeakerRecyclerViewAdapter adapter;

    public static SpeakerListFragment newInstance() {
        return new SpeakerListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_speaker_list, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        speakersRecyclerView = view.findViewById(R.id.speakers_recycler_view);
        speakersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity) getActivity()).loadInfo();

        return view;
    }

    public void setAdapter(ArrayList<SpeakerInfo> speakersInfo, ArrayList<LectureInfo> lecturesInfo) {
        adapter = new SpeakerRecyclerViewAdapter(getContext(), speakersInfo, lecturesInfo);
        speakersRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        ((MainActivity) getActivity()).loadInfo();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
