package com.zawlynn.capstoneproject.ui.podcast.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.zawlynn.capstoneproject.ui.podcast.event.IPodcastListener;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.capstoneproject.view.MediaSeekBar;


public class MediaControllerFragment extends Fragment implements
        View.OnClickListener {


    private static final String TAG = "MediaControllerFragment";


    // UI Components
    private TextView mSongTitle;
    private ImageView mPlayPause;
    private MediaSeekBar mSeekBarAudio;


    // Vars
    private IPodcastListener mIMainActivity;
    private MediaMetadataCompat mSelectedMedia;
    private boolean mIsPlaying;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_media_controller, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSongTitle = view.findViewById(R.id.media_song_title);
        mPlayPause = view.findViewById(R.id.play_pause);
        mSeekBarAudio = view.findViewById(R.id.seekbar_audio);

        mPlayPause.setOnClickListener(this);

        if(savedInstanceState != null){
            mSelectedMedia = savedInstanceState.getParcelable("selected_media");
            if(mSelectedMedia != null){
                setMediaTitle(mSelectedMedia);
                setIsPlaying(savedInstanceState.getBoolean("is_playing"));
            }
        }
    }

    public MediaSeekBar getMediaSeekBar(){
        return mSeekBarAudio;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.play_pause){
            mIMainActivity.playPause();
        }
    }

    public void setIsPlaying(boolean isPlaying){
        if(isPlaying){
            Glide.with(getActivity())
                    .load(R.drawable.ic_pause_circle_outline_white_24dp)
                    .into(mPlayPause);
        }
        else{
            Glide.with(getActivity())
                    .load(R.drawable.ic_play_circle_outline_white_24dp)
                    .into(mPlayPause);
        }
        mIsPlaying = isPlaying;
    }

    public void setMediaTitle(MediaMetadataCompat mediaItem){
        mSelectedMedia = mediaItem;
        mSongTitle.setText(mediaItem.getDescription().getTitle());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIMainActivity = (IPodcastListener) getActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("selected_media", mSelectedMedia);
        outState.putBoolean("is_playing", mIsPlaying);
    }
}

































