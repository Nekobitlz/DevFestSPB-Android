package com.nekobitlz.devfest_spb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/*
    Main menu on which is a list of lectures
*/
public class MainActivity extends AppCompatActivity {
    private ArrayList<LectureInformation> lecturesInformation;
    private String speakerName, date, address, title, label, description;

    private static LectureInformation currentLectureInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lecturesList = findViewById(R.id.lecturesList);
        XmlPullParser lectureParser = getResources().getXml(R.xml.lectures);

        this.lecturesInformation = new ArrayList<>();

        parseXml(lectureParser);

        final ArrayAdapter<LectureInformation> adapter = new InformationAdapter(this);
        lecturesList.setAdapter(adapter); //

        lecturesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                //If user has clicked on one of the list items,
                // he is transferred to the page with the selected lecture
                currentLectureInformation = lecturesInformation.get(position);

                startActivity(new Intent(getBaseContext(), LectureDescriptionActivity.class));
                finish();
            }
        });
    }

    /*
        Parses XML with lectures and writes all information into the list
    */
    private void parseXml(XmlPullParser parser) {
        try {
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("lecture")) {
                    //Gets xml attributes
                    address = parser.getAttributeValue(0);
                    date = parser.getAttributeValue(1);
                    description = parser.getAttributeValue(2);
                    label = parser.getAttributeValue(3);
                    speakerName = parser.getAttributeValue(4);
                    title = parser.getAttributeValue(5);

                    //Adds lectures text in one big list from which we can then take items
                    lecturesInformation.add(
                            new LectureInformation(speakerName, date, address, title, label, description));
                }

                parser.next();
            }
        } catch (Throwable t) {
            Toast.makeText(this,
                    "Error loading XML-document: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /*
       Gets the last selected lecture
    */
    protected static LectureInformation getCurrentLectureInformation() {
        return currentLectureInformation;
    }

    /*
        Redefining adapter in order to work with our "LectureInformation" class
    */
    private class InformationAdapter extends ArrayAdapter<LectureInformation> {

        public InformationAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2, lecturesInformation);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LectureInformation lectureInformation = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.simple_list_item_2, null);
            }

            //Top of the item (title)
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(lectureInformation.getTitle());

            //Bottom of the item (content)
            ((TextView) convertView.findViewById(android.R.id.text2))
                    .setText(lectureInformation.getSpeakerName() + "\n"
                            + lectureInformation.getDate() + "\n"
                            + lectureInformation.getLabel());

            return convertView;
        }
    }
}
