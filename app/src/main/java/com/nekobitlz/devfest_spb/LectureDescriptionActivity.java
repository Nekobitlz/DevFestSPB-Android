package com.nekobitlz.devfest_spb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
    Activity with full information about the speaker lecture
*/
public class LectureDescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button lectureLabel;
    private Button mainMenu;

    private TextView speakerName;
    private TextView speakerDescription;

    private TextView lectureDate;
    private TextView lectureDescription;
    private TextView lectureTitle;
    private TextView lectureAddress;

    private LectureInformation currentLecture;
    private SpeakerInformation currentSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_description);

        lectureLabel = findViewById(R.id.label);
        mainMenu = findViewById(R.id.all_lectures);
        speakerName = findViewById(R.id.speaker_name);
        speakerDescription = findViewById(R.id.speaker_description2);
        lectureDate = findViewById(R.id.lecture_date);
        lectureDescription = findViewById(R.id.description);
        lectureTitle = findViewById(R.id.title);
        lectureAddress = findViewById(R.id.address);

        //Obtains information about the selected lecture from Speaker Activity
        currentLecture = (LectureInformation) getIntent().getSerializableExtra("lecture");
        currentSpeaker = (SpeakerInformation) getIntent().getSerializableExtra("speaker");

        mainMenu.setOnClickListener(this);
        lectureLabel.setOnClickListener(this);

        setup();
    }

    /*
        Installs information from the selected lecture on views
    */
    private void setup() {
        //Sets lecture label background color
        switch (currentLecture.getLabel()) {
            case "Android" : lectureLabel.setBackgroundColor(Color.parseColor("#ff460e"));
                break;
            case "Frontend" : lectureLabel.setBackgroundColor(Color.parseColor("#0e6bff"));
                break;
            case "Common" : lectureLabel.setBackgroundColor(Color.parseColor("#5501cd"));
                break;
        }

        speakerName.setText(currentSpeaker.getName());
        speakerDescription.setText(currentSpeaker.getPosition() + " " + currentSpeaker.getLocation());

        lectureDate.setText(currentLecture.getDate());
        lectureLabel.setText(currentLecture.getLabel());
        lectureAddress.setText(currentLecture.getAddress());
        lectureTitle.setText(currentLecture.getTitle());
        lectureDescription.setText(currentLecture.getDescription());
    }

    /*
        Reads user clicks:
            "all_lectures" -> Returns to main menu
            "label" -> Shows a toast
    */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_lectures: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;

            case R.id.label: {
                Toast.makeText(this, "HEY, DON'T CLICK PLS, IT'S JUST A TAG", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    /*
        If "back" button is pressed - returns to speaker activity
    */
    @Override
    public void onBackPressed() {
        finish();
    }
}
