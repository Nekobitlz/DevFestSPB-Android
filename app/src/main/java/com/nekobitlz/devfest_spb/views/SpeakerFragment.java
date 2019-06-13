package com.nekobitlz.devfest_spb.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class SpeakerFragment extends Fragment implements View.OnClickListener {

    private static final String lecture = "lecture";
    private static final String speaker = "speaker";

    private LectureInfo currentLecture;
    private SpeakerInfo currentSpeaker;

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

    public static SpeakerFragment newInstance(LectureInfo currentLecture, SpeakerInfo currentSpeaker) {
        SpeakerFragment fragment = new SpeakerFragment();
        Bundle args = new Bundle();

        args.putSerializable(lecture, currentLecture);
        args.putSerializable(speaker, currentSpeaker);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentLecture = (LectureInfo) getArguments().getSerializable("lecture");
            currentSpeaker = (SpeakerInfo) getArguments().getSerializable("speaker");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_speaker, container, false);

        speakerName = view.findViewById(R.id.speaker_name);
        speakerImage = view.findViewById(R.id.speaker_image);
        speakerFlag = view.findViewById(R.id.speaker_flag);
        speakerPosition = view.findViewById(R.id.speaker_position);
        speakerLocation = view.findViewById(R.id.speaker_location);

        twitter = view.findViewById(R.id.twitter);
        telegram = view.findViewById(R.id.telegram);
        github = view.findViewById(R.id.github);

        speakerLabel = view.findViewById(R.id.speaker_label);
        speakerDescription = view.findViewById(R.id.speaker_description);
        speakerTitle = view.findViewById(R.id.speaker_title);
        speakerAddress = view.findViewById(R.id.speaker_address);
        speakerDate = view.findViewById(R.id.speaker_date);

        speakerTitle.setOnClickListener(this);
        speakerLabel.setOnClickListener(this);
        twitter.setOnClickListener(this);
        telegram.setOnClickListener(this);
        github.setOnClickListener(this);

        setup(currentLecture);
        setup(currentSpeaker);

        return view;
    }

    /*
       Installs information from the speaker lecture on views
   */
    private void setup(LectureInfo currentLecture) {
        speakerLabel.setBackgroundColor(currentLecture.getBackgroundColor());
        speakerLabel.setText(currentLecture.getLabel());
        speakerAddress.setText(currentLecture.getAddress());
        speakerTitle.setText(currentLecture.getTitle());
        speakerDate.setText(currentLecture.getDate());
    }

    /*
        Installs information from the selected speaker on views
    */
    private void setup(SpeakerInfo currentSpeaker) {
        String flagImageName = currentSpeaker.getFlagImage();

        final int speakerImageResource = getResources().getIdentifier(
                currentSpeaker.getImage(), "drawable", getContext().getPackageName()
        );

        int flagImage = getResources().getIdentifier(
                flagImageName, "drawable", getContext().getPackageName()
        );

        // loading image from URL
        Picasso.with(getContext())
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
        speakerName.setText(currentSpeaker.getFullName());
        speakerPosition.setText(currentSpeaker.getPosition());
        speakerLocation.setText(currentSpeaker.getLocation());
        speakerDescription.setText(currentSpeaker.getAbout());

        // If speaker has link, it will be shown
        if (currentSpeaker.getLinks() != null) {
            String twitterLink = currentSpeaker.getLinks().getTwitter();
            twitter.setVisibility(currentSpeaker.getVisibility(twitterLink));

            String telegramLink = currentSpeaker.getLinks().getTelegram();
            telegram.setVisibility(currentSpeaker.getVisibility(telegramLink));

            String githubLink = currentSpeaker.getLinks().getGithub();
            github.setVisibility(currentSpeaker.getVisibility(githubLink));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speaker_title: {
                LectureFragment lectureFragment = LectureFragment.newInstance(currentLecture, currentSpeaker);

                FragmentManager fragmentManager = ((MainActivity) getContext()).getSupportFragmentManager();
                lectureFragment.show(fragmentManager, "lectureFragment");
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
                Toast.makeText(getContext(), "HEY, DON'T CLICK PLS, IT'S JUST A TAG", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
}