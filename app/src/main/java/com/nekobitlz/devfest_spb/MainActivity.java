package com.nekobitlz.devfest_spb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;
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

        speakersRecyclerView.setAdapter(new Adapter(speakersInformation));
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
                            new SpeakerInformation(name, image, position, location, speakerDescription, lecture));
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
                            new LectureInformation(speakerName, date, address, title, label, lectureDescription));
                }

                parser.next();
            }
        } catch (Throwable t) {
            Toast.makeText(this,
                    "Error loading lecture list: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /*
        Adapter that holds data and links it to the list
    */
    public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<SpeakerInformation> speakersInformation;

        public Adapter(ArrayList<SpeakerInformation> speakersInformation) {
            this.speakersInformation = speakersInformation;
        }

        /*
            Creates a new ViewHolder object whenever RecyclerView needs it
        */
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.speakers_list_view, viewGroup, false);

            return new ViewHolder(itemView);
        }

        /*
            Takes a ViewHolder object and sets the necessary data
            for the corresponding row in the view component
        */
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            viewHolder = new ViewHolder(viewHolder.itemView);
            final SpeakerInformation currentSpeaker = speakersInformation.get(i);
            final LectureInformation currentLecture = getCurrentLecture(currentSpeaker);

            //Getting image from resources by image name
            String imageName = currentSpeaker.getImage();
            int image = getResources().getIdentifier(imageName, "drawable", getPackageName());

            //Setting all attributes in view
            final String speakerName = currentSpeaker.getName();
            String speakerPosition = currentSpeaker.getPosition();
            String speakerLocation = currentSpeaker.getLocation();

            ((ViewHolder) viewHolder).getSpeakerImage().setImageResource(image);
            ((ViewHolder) viewHolder).getSpeakerName().setText(speakerName.toUpperCase());
            ((ViewHolder) viewHolder).getSpeakerPosition().setText(speakerPosition);
            ((ViewHolder) viewHolder).getSpeakerLocation().setText(speakerLocation);
            ((ViewHolder) viewHolder).getViewProfile().setOnClickListener(new View.OnClickListener() {
                /*
                    If user has clicked on one of the list items,
                    information about selected speaker is transferred to SpeakerDescription activity
                    and user is transferred to the selected page
                */
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), SpeakerDescriptionActivity.class);

                    intent.putExtra("lecture", currentLecture);
                    intent.putExtra("speaker", currentSpeaker);

                    startActivity(intent);
                }
            });
        }

        /*
            Returns the total number of list items
        */
        @Override
        public int getItemCount() {
            return speakersInformation.size();
        }

        /*
            Gets a lecture from the name of the desired speaker
        */
        private LectureInformation getCurrentLecture(SpeakerInformation speakerInformation) {
            for (LectureInformation lecture: lecturesInformation) {
                if (lecture.getSpeakerName().equals(speakerInformation.getName())) {
                    return lecture;
                }
            }

            return null;
        }

        /*
            A class that stores information about used views
        */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private CircleImageView speakerImage;
            private TextView speakerName;
            private TextView speakerPosition;
            private TextView speakerLocation;
            private Button viewProfile;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                speakerImage = itemView.findViewById(R.id.speaker_image);
                speakerName = itemView.findViewById(R.id.speaker_name);
                speakerPosition = itemView.findViewById(R.id.speaker_position);
                speakerLocation = itemView.findViewById(R.id.speaker_location);
                viewProfile = itemView.findViewById(R.id.view_profile);
            }

            /*
                GETTERS
            */
            public TextView getSpeakerName() {
                return speakerName;
            }

            public TextView getSpeakerPosition() {
                return speakerPosition;
            }

            public TextView getSpeakerLocation() {
                return speakerLocation;
            }

            public CircleImageView getSpeakerImage() {
                return speakerImage;
            }

            public Button getViewProfile() {
                return viewProfile;
            }
        }
    }
}

