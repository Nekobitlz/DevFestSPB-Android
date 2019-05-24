package com.nekobitlz.devfest_spb.views;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.nekobitlz.devfest_spb.data.App;
import com.nekobitlz.devfest_spb.data.AppDatabase;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;
import com.nekobitlz.devfest_spb.adapters.SpeakerRecyclerViewAdapter;
import com.nekobitlz.devfest_spb.network.*;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/*
    Main menu on which is a list of lectures
*/
public class MainActivity extends AppCompatActivity {

    private LoadInfoTask loadInfoTask;
    private RecyclerView speakersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakersRecyclerView = findViewById(R.id.speakers_recycler_view);
        speakersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadInfoTask = new LoadInfoTask(this, speakersRecyclerView);
        loadInfoTask.execute();
    }

    @Override
    protected void onDestroy() {
        loadInfoTask.cancel(true);
        loadInfoTask = null;
        super.onDestroy();
    }

    /*
        Asynchronous data loading
    */
    public static class LoadInfoTask extends AsyncTask<Void, Void, Void> {

        private ServerApi api;
        private ArrayList<LectureInfo> lecturesInfo;
        private ArrayList<SpeakerInfo> speakersInfo;

        private String speakerName;
        private String date;
        private String address;
        private String title;
        private String label;
        private String lectureDescription;

        private String id;
        private String firstName;
        private String lastName;
        private String image;
        private String jobTitle;
        private String company;
        private String location;
        private String about;
        private String flagImage;

        private XmlPullParser speakerParser;
        private XmlPullParser lectureParser;

        private WeakReference<Context> weakContext;
        private WeakReference<RecyclerView> weakSpeakersRecycler;
        private SpeakerRecyclerViewAdapter adapter;

        public LoadInfoTask(Context context, RecyclerView speakersRecyclerView) {

            weakContext = new WeakReference<>(context);
            weakSpeakersRecycler = new WeakReference<>(speakersRecyclerView);

            if (weakContext != null) {
                lectureParser = weakContext.get().getResources().getXml(R.xml.lectures);
                speakerParser = weakContext.get().getResources().getXml(R.xml.speakers);
            }

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
                Call<ApiData> speakerCall = api.getData();

                speakerCall.enqueue(new Callback<ApiData>() {
                    @Override
                    public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                        ApiData data = response.body();

                        speakersInfo = data.getSpeakersList();
                        lecturesInfo = data.getSchedule().getLecturesList();

                        adapter = new SpeakerRecyclerViewAdapter(
                                weakContext.get(), speakersInfo, lecturesInfo
                        );
                        weakSpeakersRecycler.get().setAdapter(adapter);

                        //Saving data in database
                        db.speakerDao().deleteAll();
                        db.speakerDao().insertAll(speakersInfo.toArray(new SpeakerInfo[0]));

                        db.lectureDao().deleteAll();
                        db.lectureDao().insertAll(lecturesInfo.toArray(new LectureInfo[0]));

                        //just check for debug
                        for (SpeakerInfo i : speakersInfo) {
                            Log.e("SpeakerData", i.getFirstName() + " " + i.getLastName() + "");
                        }

                        for (LectureInfo i : lecturesInfo) {
                            Log.e("LectureData", i.getTitle() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiData> call, Throwable t) {
                        Toast.makeText(weakContext.get(),
                                "Failed to load file from server: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Json Data Error", t.getMessage());

                        //If information can't be downloaded from the server - it's parsed from xml
                        try {
                            parseSpeakerXml(speakerParser);
                            parseLecturesXml(lectureParser);

                            Toast.makeText(weakContext.get(),
                                    "Data successfully uploaded offline!", Toast.LENGTH_LONG).show();
                        } catch (XmlPullParserException e) {
                            Toast.makeText(weakContext.get(),
                                    "Couldn't parse data: " + e.toString(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(weakContext.get(),
                                    "Error reading file: " + e.toString(), Toast.LENGTH_LONG).show();
                        } catch (Throwable throwable) {
                            Toast.makeText(weakContext.get(),
                                    "Error loading speaker list: " + throwable.toString(), Toast.LENGTH_LONG).show();
                        }

                        adapter = new SpeakerRecyclerViewAdapter(
                                weakContext.get(), speakersInfo, lecturesInfo
                        );
                        weakSpeakersRecycler.get().setAdapter(adapter);
                    }
                });
            } else {
                //Restoring data from db
                List<SpeakerInfo> speakersInfo = db.speakerDao().getAll();
                List<LectureInfo> lecturesInfo = db.lectureDao().getAll();

                adapter = new SpeakerRecyclerViewAdapter(
                        weakContext.get(),
                        (ArrayList<SpeakerInfo>) speakersInfo,
                        (ArrayList<LectureInfo>) lecturesInfo
                );
                weakSpeakersRecycler.get().setAdapter(adapter);
            }

            return null;
        }

        /*
            Parses XML with speakers and writes all information into the list
        */
        private void parseSpeakerXml(XmlPullParser parser) throws XmlPullParserException, IOException {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("speaker")) {
                    //Gets xml attributes
                    about = parser.getAttributeValue(0);
                    company = parser.getAttributeValue(1);
                    firstName = parser.getAttributeValue(2);
                    flagImage = parser.getAttributeValue(3);
                    id = parser.getAttributeValue(4);
                    image = parser.getAttributeValue(5);
                    jobTitle = parser.getAttributeValue(6);
                    lastName = parser.getAttributeValue(7);
                    location = parser.getAttributeValue(8);

                    //Adds speakers attributes in one big list from which we can then take items
                    speakersInfo.add(
                            new SpeakerInfo(id, firstName, lastName, image,
                                    jobTitle, company, location, about, flagImage)
                    );
                }

                parser.next();
            }
        }

        /*
            Parses XML with lectures and writes all information into the list
        */
        private void parseLecturesXml(XmlPullParser parser) throws XmlPullParserException, IOException {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("lecture")) {
                    //Gets xml attributes
                    address = parser.getAttributeValue(0);
                    date = parser.getAttributeValue(1);
                    lectureDescription = parser.getAttributeValue(2);
                    label = parser.getAttributeValue(3);
                    speakerName = parser.getAttributeValue(4);
                    title = parser.getAttributeValue(5);

                    //Adds lectures attributes in one big list from which we can then take items
                    lecturesInfo.add(
                            new LectureInfo(speakerName, date, address,
                                    title, label, lectureDescription)
                    );
                }

                parser.next();
            }
        }
    }
}