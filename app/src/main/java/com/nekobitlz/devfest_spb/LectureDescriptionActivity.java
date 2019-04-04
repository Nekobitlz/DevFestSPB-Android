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
    Activity with full information about the selected lecture
*/
public class LectureDescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button label;
    private Button mainMenu;

    private TextView speakerName;
    private TextView description;
    private TextView title;
    private TextView address;

    private LectureInformation currentLectureInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_description);

        label = findViewById(R.id.label);
        mainMenu = findViewById(R.id.all_lectures);
        speakerName = findViewById(R.id.speaker_name);
        description = findViewById(R.id.description);
        title = findViewById(R.id.title);
        address = findViewById(R.id.address);

        //Obtains information about the selected lecture from Main Activity
        currentLectureInformation = (LectureInformation) getIntent().getSerializableExtra("LectureInformation");

        mainMenu.setOnClickListener(this);
        label.setOnClickListener(this);

        setup(currentLectureInformation);
    }

    /*
        Installation of information from the views into this activity
    */
    private void setup(LectureInformation currentLectureInformation) {
        //sets label background color
        if (android.text.TextUtils.equals(currentLectureInformation.getLabel(), "Android")) {
            label.setBackgroundColor(Color.parseColor("#ff460e"));
        } else {
            label.setBackgroundColor(Color.parseColor("#0e6bff"));
        }

        label.setText(currentLectureInformation.getLabel());
        address.setText(currentLectureInformation.getAddress());
        speakerName.setText(currentLectureInformation.getSpeakerName());
        title.setText(currentLectureInformation.getTitle());
        description.setText(currentLectureInformation.getDescription());
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
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            break;

            case R.id.label: {
                Toast.makeText(this, "HEY, DON'T CLICK PLS, IT'S JUST A TAG", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    /*
        If "back" button is pressed - returns to main menu
    */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}
