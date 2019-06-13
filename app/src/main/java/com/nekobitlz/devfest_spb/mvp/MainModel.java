package com.nekobitlz.devfest_spb.mvp;

import android.os.AsyncTask;
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
import java.util.ArrayList;

public class MainModel implements MainContract.Model {

    private ArrayList<LectureInfo> lecturesInfo;
    private ArrayList<SpeakerInfo> speakersInfo;

    public MainModel() {

        this.lecturesInfo = new ArrayList<>();
        this.speakersInfo = new ArrayList<>();
    }

    public void loadInfo() {
        new LoadInfoTask(this).execute();
    }

    @Override
    public ArrayList<SpeakerInfo> getSpeakersInfo() {
        return speakersInfo;
    }

    @Override
    public ArrayList<LectureInfo> getLecturesInfo() {
        return lecturesInfo;
    }

    public void setLecturesInfo(ArrayList<LectureInfo> lecturesInfo) {
        this.lecturesInfo = lecturesInfo;
    }

    public void setSpeakersInfo(ArrayList<SpeakerInfo> speakersInfo) {
        this.speakersInfo = speakersInfo;
    }

    public static class LoadInfoTask extends AsyncTask<Void, Void, Void> {

        private ServerApi api;
        final AppDatabase db;
        private MainModel mainModel;

        private ArrayList<LectureInfo> lecturesInfo;
        private ArrayList<SpeakerInfo> speakersInfo;

        public LoadInfoTask(MainModel mainModel) {
            api = new NetworkModule().serverApi;
            db = App.getInstance().getDatabase();

            this.mainModel = mainModel;
            this.lecturesInfo = new ArrayList<>();
            this.speakersInfo = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //If we don't have records in the database,
            // then need to download data from API
            if (databaseIsEmpty()) {
                loadDataFromApi();
                saveData();
                restoreData();
            } else {
                restoreData();
            }

            mainModel.setLecturesInfo(lecturesInfo);
            mainModel.setSpeakersInfo(speakersInfo);

            return null;
        }

        public boolean databaseIsEmpty() {
            return db.speakerDao().getAll().isEmpty();
        }

        public void loadDataFromApi() {
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

        public void restoreData() {
            speakersInfo = (ArrayList<SpeakerInfo>) db.speakerDao().getAll();
            lecturesInfo = (ArrayList<LectureInfo>) db.lectureDao().getAll();
        }

        public void saveData() {
            db.speakerDao().deleteAll();
            db.speakerDao().insertAll(speakersInfo.toArray(new SpeakerInfo[0]));

            db.lectureDao().deleteAll();
            db.lectureDao().insertAll(lecturesInfo.toArray(new LectureInfo[0]));
        }
    }
}
