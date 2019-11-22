package com.zawlynn.capstoneproject.ui.podcast.event;

import android.support.v4.media.MediaMetadataCompat;

import com.zawlynn.capstoneproject.pojo.Episode;
import com.zawlynn.capstoneproject.utils.MyPreferenceManager;

import java.util.List;


public interface IPodcastListener {

    void setActionBarTitle(String title);

    void playPause();

    void setEpisode(List<Episode> episode);

    void onMediaSelected(String playlistId, MediaMetadataCompat mediaItem, int position);

    MyPreferenceManager getMyPreferenceManager();

    void onPlayAudio(int id);
}
