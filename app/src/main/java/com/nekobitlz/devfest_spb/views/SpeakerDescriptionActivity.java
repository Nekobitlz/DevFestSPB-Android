package com.nekobitlz.devfest_spb.views;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.nekobitlz.devfest_spb.data.Tracks.*;

/*
    Activity with full information about the selected speaker
*/
public class SpeakerDescriptionActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView speakerName;
    private CircleImageView speakerImage;
    private CircleImageView speakerFlag;
    private TextView speakerLocation;
    private TextView speakerPosition;
    private TextView speakerDescription;
    private CircleImageView twitter;
    private CircleImageView github;
    private CircleImageView telegram;

    private Button speakerLabel;
    private TextView speakerAddress;
    private TextView speakerTitle;
    private TextView speakerDate;

    private SpeakerInfo currentSpeaker;
    private LectureInfo currentLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_description);

        speakerName = findViewById(R.id.speaker_name);
        speakerImage = findViewById(R.id.speaker_image);
        speakerFlag = findViewById(R.id.speaker_flag);
        speakerPosition = findViewById(R.id.speaker_position);
        speakerLocation = findViewById(R.id.speaker_location);

        twitter = findViewById(R.id.twitter);
        telegram = findViewById(R.id.telegram);
        github = findViewById(R.id.github);

        speakerLabel = findViewById(R.id.speaker_label);
        speakerDescription = findViewById(R.id.speaker_description);
        speakerTitle = findViewById(R.id.speaker_title);
        speakerAddress = findViewById(R.id.speaker_address);
        speakerDate = findViewById(R.id.speaker_date);

        //Obtains information about the selected lecture from Main Activity
        currentLecture = (LectureInfo) getIntent().getSerializableExtra("lecture");
        currentSpeaker = (SpeakerInfo) getIntent().getSerializableExtra("speaker");

        speakerTitle.setOnClickListener(this);
        speakerLabel.setOnClickListener(this);
        twitter.setOnClickListener(this);
        telegram.setOnClickListener(this);
        github.setOnClickListener(this);

        setup(currentLecture);
        setup(currentSpeaker);
    }

    /*
        Installs information from the speaker lecture on views
    */
    private void setup(LectureInfo currentLecture) {
        //Sets label background color
        switch (currentLecture.getLabel().toLowerCase()) {
            case ANDROID_NAME : speakerLabel.setBackgroundColor(Color.parseColor(ANDROID.getColor()));
            break;
            case FRONTEND_NAME : speakerLabel.setBackgroundColor(Color.parseColor(FRONTEND.getColor()));
            break;
            case COMMON_NAME : speakerLabel.setBackgroundColor(Color.parseColor(COMMON.getColor()));
            break;
        }

        speakerLabel.setText(currentLecture.getLabel());
        speakerAddress.setText("Room " + currentLecture.getAddress());
        speakerTitle.setText(currentLecture.getTitle());
        speakerDate.setText(currentLecture.getDate());
    }

    /*
        Installs information from the selected speaker on views
    */
    private void setup(SpeakerInfo currentSpeaker) {
        String company = currentSpeaker.getCompany().isEmpty() ? "" : " at " + currentSpeaker.getCompany();
        final int speakerImageResource = getResources().getIdentifier(
                currentSpeaker.getImage(), "drawable", getPackageName()
        );

        String flagImageName = "flag_" + currentSpeaker.getFlagImage();
        int flagImage = getResources().getIdentifier(
                flagImageName, "drawable", getPackageName()
        );

        // loading image from URL
        Picasso.with(this)
                .load(currentSpeaker.getImage())
                .into(speakerImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        /* Celebrate success! */
                    }

                    @Override
                    public void onError() {
                        // Load the local image
                        speakerImage.setImageResource(speakerImageResource);
                    }
                });

        speakerFlag.setImageResource(flagImage);
        speakerName.setText(currentSpeaker.getFirstName() + " " + currentSpeaker.getLastName());
        speakerPosition.setText(currentSpeaker.getJobTitle() + company);
        speakerLocation.setText(currentSpeaker.getLocation());
        speakerDescription.setText(currentSpeaker.getAbout());

        // If speaker has link, it will be shown
        if (currentSpeaker.getLinks() != null) {
            if (currentSpeaker.getLinks().getTwitter() != null) {
                twitter.setVisibility(View.VISIBLE);
            }

            if (currentSpeaker.getLinks().getTelegram() != null) {
                telegram.setVisibility(View.VISIBLE);
            }

            if (currentSpeaker.getLinks().getGithub() != null) {
                github.setVisibility(View.VISIBLE);
            }
        }
    }

    /*
       Reads user clicks:
           "speaker_title" -> Opens activity of the selected lecture
           "label" -> Shows a toast
   */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speaker_title: {
                Intent intent = new Intent(this, LectureDescriptionActivity.class);
                intent.putExtra("lecture", currentLecture);
                intent.putExtra("speaker", currentSpeaker);
                startActivity(intent);
            }
            break;

            case R.id.twitter: {
                String twitterUrl = currentSpeaker.getLinks().getTwitter();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl));
                startActivity(browserIntent);
            }
            break;

            case R.id.telegram: {
                String telegramUrl = currentSpeaker.getLinks().getTelegram();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl));
                startActivity(browserIntent);
            }
            break;

            case R.id.github: {
                String githubUrl = currentSpeaker.getLinks().getGithub();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
                startActivity(browserIntent);
            }
            break;

            case R.id.speaker_label: {
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
