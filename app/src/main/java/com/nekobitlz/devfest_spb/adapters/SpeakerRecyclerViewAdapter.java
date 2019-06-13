package com.nekobitlz.devfest_spb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.nekobitlz.devfest_spb.R;
import com.nekobitlz.devfest_spb.data.LectureInfo;
import com.nekobitlz.devfest_spb.data.SpeakerInfo;
import com.nekobitlz.devfest_spb.views.MainActivity;
import com.nekobitlz.devfest_spb.views.SpeakerFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

/*
       Adapter that holds data and links it to the list
*/
public class SpeakerRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String FRAGMENT_TAG = "speaker_fragment";

    private ArrayList<SpeakerInfo> speakerInfo;
    private ArrayList<LectureInfo> lectureInfo;
    private Context context;
    private MainActivity view;

    public SpeakerRecyclerViewAdapter(
            Context context,
            ArrayList<SpeakerInfo> speakerInfo,
            ArrayList<LectureInfo> lectureInfo) {
        this.speakerInfo = speakerInfo;
        this.lectureInfo = lectureInfo;
        this.context = context;

        view = (MainActivity) context;
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
        final SpeakerInfo currentSpeaker = speakerInfo.get(i);
        final LectureInfo currentLecture = getCurrentLecture(currentSpeaker);

        //Getting image from resources by image name
        String imageName = currentSpeaker.getImage();
        final int imageResource = context.getResources().getIdentifier(
                imageName, "drawable", context.getPackageName()
        );

        String flagImageName = currentSpeaker.getFlagImage();
        int flagImage = context.getResources().getIdentifier(
                flagImageName, "drawable", context.getPackageName()
        );

        //Setting all attributes in view
        final String speakerName = currentSpeaker.getFullName();
        String speakerPosition = currentSpeaker.getPosition();
        String speakerLocation = currentSpeaker.getLocation();

        final RecyclerView.ViewHolder finalViewHolder = viewHolder;
        Picasso.with(context)
                .load(imageName)
                .into(((ViewHolder) viewHolder).getSpeakerImage(), new Callback() {
                    @Override
                    public void onSuccess() {
                        /* Celebrate success! */
                    }

                    @Override
                    public void onError() {
                        // Load the local image
                        ((ViewHolder) finalViewHolder).getSpeakerImage().setImageResource(imageResource);
                    }
                });

        ((ViewHolder) viewHolder).getSpeakerFlag().setImageResource(flagImage);
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

                SpeakerFragment speakerFragment = SpeakerFragment.newInstance(currentLecture, currentSpeaker);

                FragmentManager fragmentManager = view.getSupportFragmentManager();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, speakerFragment, FRAGMENT_TAG);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
    }

    /*
        Returns the total number of list items
    */
    @Override
    public int getItemCount() {
        return speakerInfo.size();
    }

    /*
        Gets a lecture from the name of the desired speaker
    */
    private LectureInfo getCurrentLecture(SpeakerInfo speakerInfo) {
        for (LectureInfo lecture : lectureInfo) {
            if (lecture.getSpeakerId().equals(speakerInfo.getId())) {
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
        private CircleImageView speakerFlag;
        private TextView speakerName;
        private TextView speakerPosition;
        private TextView speakerLocation;
        private Button viewProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            speakerImage = itemView.findViewById(R.id.speaker_image);
            speakerFlag = itemView.findViewById(R.id.speaker_flag);
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

        public CircleImageView getSpeakerFlag() {
            return speakerFlag;
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