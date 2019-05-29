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

public class SpeakerListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final static String FRAGMENT_TAG = "speaker_fragment";

    private MainActivity.LoadInfoTask loadInfoTask;
    private RecyclerView speakersRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

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

        loadInfoTask = new MainActivity.LoadInfoTask(getContext(), speakersRecyclerView);
        loadInfoTask.execute();

        return view;
    }

    @Override
    public void onRefresh() {
        new MainActivity.LoadInfoTask(getContext(), speakersRecyclerView).execute();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        loadInfoTask.cancel(true);
        loadInfoTask = null;
        super.onDestroy();
    }
}
