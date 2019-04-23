package com.nekobitlz.devfest_spb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

/*
       Adapter that holds data and links it to the list
*/
public class SpeakerRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SpeakerInformation> speakersInformation;
    private ArrayList<LectureInformation> lectureInformation;
    private Context context;

    public SpeakerRecyclerViewAdapter(
            Context context,
            ArrayList<SpeakerInformation> speakersInformation,
            ArrayList<LectureInformation> lectureInformations) {
        this.speakersInformation = speakersInformation;
        this.lectureInformation = lectureInformations;
        this.context = context;
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
        int image = context.getResources().getIdentifier(
                imageName, "drawable", context.getPackageName()
        );

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
                Intent intent = new Intent(context, SpeakerDescriptionActivity.class);

                intent.putExtra("lecture", currentLecture);
                intent.putExtra("speaker", currentSpeaker);

                context.startActivity(intent);
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
        for (LectureInformation lecture : lectureInformation) {
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
