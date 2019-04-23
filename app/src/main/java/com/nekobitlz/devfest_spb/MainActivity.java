package com.nekobitlz.devfest_spb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/*
    Main menu on which is a list of lectures
*/
public class MainActivity extends AppCompatActivity {
    private ArrayList<LectureInformation> lecturesInformation;
    private ArrayList<SpeakerInformation> speakersInformation;

    private String speakerName;
    private String date;
    private String address;
    private String title;
    private String label;
    private String lectureDescription;

    private String speakerDescription;
    private String image;
    private String lecture;
    private String location;
    private String name;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView speakersRecyclerView = findViewById(R.id.speakers_recycler_view);
        speakersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        XmlPullParser lectureParser = getResources().getXml(R.xml.lectures);
        XmlPullParser speakerParser = getResources().getXml(R.xml.speakers);

        this.lecturesInformation = new ArrayList<>();
        this.speakersInformation = new ArrayList<>();

        parseLecturesXml(lectureParser);
        parseSpeakerXml(speakerParser);

        speakersRecyclerView.setAdapter(
                new SpeakerRecyclerViewAdapter(this, speakersInformation, lecturesInformation)
        );
    }

    /*
        Parses XML with speakers and writes all information into the list
    */
    private void parseSpeakerXml(XmlPullParser parser) {
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("speaker")) {
                    //Gets xml attributes
                    speakerDescription = parser.getAttributeValue(0);
                    image = parser.getAttributeValue(1);
                    lecture = parser.getAttributeValue(2);
                    location = parser.getAttributeValue(3);
                    name = parser.getAttributeValue(4);
                    position = parser.getAttributeValue(5);

                    //Adds speakers attributes in one big list from which we can then take items
                    speakersInformation.add(
                            new SpeakerInformation(name, image, position, location, speakerDescription, lecture)
                    );
                }

                parser.next();
            }
        } catch (Throwable t) {
            Toast.makeText(this,
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
            Toast.makeText(this,
                    "Error loading lecture list: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
}