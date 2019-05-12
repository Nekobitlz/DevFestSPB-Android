package com.nekobitlz.devfest_spb.views;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.nekobitlz.devfest_spb.data.LectureInformation;
import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.data.SpeakerInformation;
import com.nekobitlz.devfest_spb.adapters.SpeakerRecyclerViewAdapter;
import com.nekobitlz.devfest_spb.network.NetworkModule;
import com.nekobitlz.devfest_spb.network.ServerApi;
import com.nekobitlz.devfest_spb.network.Speaker;
import org.xmlpull.v1.XmlPullParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

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
        private ArrayList<LectureInformation> lecturesInformation;
        private ArrayList<SpeakerInformation> speakersInformation;

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

            this.lecturesInformation = new ArrayList<>();
            this.speakersInformation = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            parseLecturesXml(lectureParser);
            Call<Speaker> speakerCall = api.getSpeakers();

            speakerCall.enqueue(new Callback<Speaker>() {
                @Override
                public void onResponse(Call<Speaker> call, Response<Speaker> response) {

                    Speaker data = response.body();
                    speakersInformation = data.getSpeakersList();
                    adapter = new SpeakerRecyclerViewAdapter(
                            weakContext.get(), speakersInformation, lecturesInformation
                    );
                    weakSpeakersRecycler.get().setAdapter(adapter);

                    //just check for debug
                    for (SpeakerInformation i: speakersInformation) {
                        Log.e("SpeakerData",i.getFirstName() + " " + i.getLastName() +"");
                    }
                }

                @Override
                public void onFailure(Call<Speaker> call, Throwable t) {
                    parseSpeakerXml(speakerParser);
                    Toast.makeText(weakContext.get(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.e("Json Data Error",t.getMessage());
                }
            });

            return null;
        }

        /*
            Parses XML with speakers and writes all information into the list
        */
        private void parseSpeakerXml(XmlPullParser parser) {
            try {
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
                        speakersInformation.add(
                                new SpeakerInformation(id, firstName, lastName, image,
                                        jobTitle, company, location, about, flagImage)
                        );
                    }

                    parser.next();
                }
            } catch (Throwable t) {
                Toast.makeText(weakContext.get(),
                        "Error loading speaker list: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }

        /*
            Parses XML with lectures and writes all information into the list
        */
        private void parseLecturesXml(XmlPullParser parser) {
            try {
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
                        lecturesInformation.add(
                                new LectureInformation(speakerName, date, address, title, label, lectureDescription)
                        );
                    }

                    parser.next();
                }
            } catch (Throwable t) {
                Toast.makeText(weakContext.get(),
                        "Error loading lecture list: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

}