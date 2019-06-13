package com.nekobitlz.devfest_spb.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;

public class LectureFragment extends DialogFragment implements View.OnClickListener {

    private static final String lecture = "lecture";
    private static final String speaker = "speaker";

    private LectureInfo currentLecture;
    private SpeakerInfo currentSpeaker;

    private Button lectureLabel;
    private Button mainMenu;

    private TextView speakerName;
    private TextView speakerDescription;

    private TextView lectureDate;
    private TextView lectureDescription;
    private TextView lectureTitle;
    private TextView lectureAddress;

    public static LectureFragment newInstance(LectureInfo currentLecture, SpeakerInfo currentSpeaker) {
        LectureFragment fragment = new LectureFragment();
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
            currentLecture = (LectureInfo) getArguments().getSerializable(lecture);
            currentSpeaker = (SpeakerInfo) getArguments().getSerializable(speaker);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lecture, container, false);

        lectureLabel = view.findViewById(R.id.label);
        mainMenu = view.findViewById(R.id.all_lectures);
        speakerName = view.findViewById(R.id.speaker_name);
        speakerDescription = view.findViewById(R.id.speaker_description2);
        lectureDate = view.findViewById(R.id.lecture_date);
        lectureDescription = view.findViewById(R.id.description);
        lectureTitle = view.findViewById(R.id.title);
        lectureAddress = view.findViewById(R.id.address);

        mainMenu.setOnClickListener(this);
        lectureLabel.setOnClickListener(this);

        setup();

        return view;
    }

    private void setup() {
        speakerName.setText(currentSpeaker.getFullName());
        speakerDescription.setText(currentSpeaker.getPosition());

        lectureDate.setText(currentLecture.getDate());
        lectureLabel.setText(currentLecture.getLabel());
        lectureLabel.setBackgroundColor(currentLecture.getBackgroundColor());
        lectureAddress.setText(currentLecture.getAddress());
        lectureTitle.setText(currentLecture.getTitle());
        lectureDescription.setText(currentLecture.getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_lectures: {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;

            case R.id.label: {
                Toast.makeText(getContext(), "HEY, DON'T CLICK PLS, IT'S JUST A TAG", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
}
