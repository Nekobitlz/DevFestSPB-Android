package com.nekobitlz.devfest_spb.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import com.nekobitlz.devfest_spb.R;
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

public class UpdateService extends IntentService {

    private ServerApi api;
    private ArrayList<LectureInfo> lecturesInfo;
    private ArrayList<SpeakerInfo> speakersInfo;
    private NotificationManager notificationManager;

    public UpdateService() {
        super("update_service");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        api = new NetworkModule().serverApi;

        this.lecturesInfo = new ArrayList<>();
        this.speakersInfo = new ArrayList<>();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final AppDatabase db = App.getInstance().getDatabase();

        loadDataFromApi();
        saveData(db);
        createNotification();
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

    private void saveData(AppDatabase db) {
        db.speakerDao().deleteAll();
        db.speakerDao().insertAll(speakersInfo.toArray(new SpeakerInfo[0]));

        db.lectureDao().deleteAll();
        db.lectureDao().insertAll(lecturesInfo.toArray(new LectureInfo[0]));
    }

    @SuppressWarnings("deprecation")
    private void createNotification() {
        String notificationText = "Service created!";

        Notification notification = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Update status")
                    .setContentText(notificationText)
                    .setTicker("Updated!")
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
        }

        notificationManager.notify(1, notification);
    }
}
