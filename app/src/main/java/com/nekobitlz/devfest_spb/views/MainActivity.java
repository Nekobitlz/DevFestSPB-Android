package com.nekobitlz.devfest_spb.views;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.adapters.SpeakerRecyclerViewAdapter;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;
import com.nekobitlz.devfest_spb.database.App;
import com.nekobitlz.devfest_spb.database.AppDatabase;
import com.nekobitlz.devfest_spb.network.ApiData;
import com.nekobitlz.devfest_spb.network.NetworkModule;
import com.nekobitlz.devfest_spb.network.ServerApi;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/*
    Main menu on which is a list of lectures
*/
public class MainActivity extends AppCompatActivity {

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
                    .addToBackStack(null)
                    .commit();
        } else {
            speakerListFragment = (SpeakerListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }
    }

    /*
        Asynchronous data loading
    */
    public static class LoadInfoTask extends AsyncTask<Void, Void, Void> {

        private ServerApi api;
        private ArrayList<LectureInfo> lecturesInfo;
        private ArrayList<SpeakerInfo> speakersInfo;

        private WeakReference<Context> weakContext;
        private WeakReference<RecyclerView> weakSpeakersRecycler;
        private SpeakerRecyclerViewAdapter adapter;

        public LoadInfoTask(Context context, RecyclerView speakersRecyclerView) {

            weakContext = new WeakReference<>(context);
            weakSpeakersRecycler = new WeakReference<>(speakersRecyclerView);

            api = new NetworkModule().serverApi;

            this.lecturesInfo = new ArrayList<>();
            this.speakersInfo = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final AppDatabase db = App.getInstance().getDatabase();

            //If we don't have records in the database,
            // then need to download data from API
            if (db.speakerDao().getAll().isEmpty()) {
                loadDataFromApi();
                saveData(db);
                restoreData(db);
            } else {
                restoreData(db);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            initAdapter();
        }

        private void initAdapter() {
            adapter = new SpeakerRecyclerViewAdapter(
                    weakContext.get(),
                    speakersInfo,
                    lecturesInfo
            );
            weakSpeakersRecycler.get().setAdapter(adapter);
        }

        private void loadDataFromApi() {
            Call<ApiData> call = api.getData();
            Response<ApiData> response = null;

            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ApiData data = response != null ? response.body() : null;

            if (data != null) {
                speakersInfo = data.getSpeakersList();
                lecturesInfo = data.getSchedule().getLecturesList();
            }
        }

        private void restoreData(AppDatabase db) {
            speakersInfo = (ArrayList<SpeakerInfo>) db.speakerDao().getAll();
            lecturesInfo = (ArrayList<LectureInfo>) db.lectureDao().getAll();
        }

        private void saveData(AppDatabase db) {
            db.speakerDao().deleteAll();
            db.speakerDao().insertAll(speakersInfo.toArray(new SpeakerInfo[0]));

            db.lectureDao().deleteAll();
            db.lectureDao().insertAll(lecturesInfo.toArray(new LectureInfo[0]));
        }
    }
}