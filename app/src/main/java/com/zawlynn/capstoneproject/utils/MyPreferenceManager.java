package com.zawlynn.capstoneproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.capstoneproject.pojo.Episode;

import static com.zawlynn.capstoneproject.utils.Constant.LAST_ARTIST;
import static com.zawlynn.capstoneproject.utils.Constant.LAST_ARTIST_IMAGE;
import static com.zawlynn.capstoneproject.utils.Constant.LAST_CATEGORY;
import static com.zawlynn.capstoneproject.utils.Constant.MEDIA_QUEUE_POSITION;
import static com.zawlynn.capstoneproject.utils.Constant.NOW_PLAYING;
import static com.zawlynn.capstoneproject.utils.Constant.PLAYLIST_ID;

public class MyPreferenceManager {

    private static final String TAG = "MyPreferenceManager";

    private SharedPreferences mPreferences;

    public MyPreferenceManager(Context mContext) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public String getPlaylistId(){
        return mPreferences.getString(PLAYLIST_ID, "");
    }

    public void savePlaylistId(String playlistId){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PLAYLIST_ID, playlistId);
        editor.apply();
    }

    public void saveQueuePosition(int position){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(MEDIA_QUEUE_POSITION, position);
        editor.apply();
    }

    public int getQueuePosition(){
        return mPreferences.getInt(MEDIA_QUEUE_POSITION, -1);
    }

    public void saveLastPlayedArtistImage(String url){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(LAST_ARTIST_IMAGE, url);
        editor.apply();
    }
    public void updateSharedPreference(Context context, Episode item, String podcastTitle) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_podcast_title_key), podcastTitle);
        editor.putString(context.getString(R.string.pref_episode_title_key), item.getTitle());
        editor.putString(context.getString(R.string.pref_episode_image_key), item.getImage());
        editor.apply();
    }
    public void updateEpisodeTitle(Context context,String title){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_episode_title_key), title);
        editor.apply();
    }
    public String getLastPlayedArtistImage(){
        return  mPreferences.getString(LAST_ARTIST_IMAGE, "");
    }

    public String getLastPlayedArtist(){
        return  mPreferences.getString(LAST_ARTIST, "");
    }

    public String getLastEpisode(){
        return  mPreferences.getString(LAST_CATEGORY, "");
    }

    public void saveLastPlayedMedia(String mediaId){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(NOW_PLAYING, mediaId);
        editor.apply();
    }

    public String getLastPlayedMedia(){
        return mPreferences.getString(NOW_PLAYING, "");
    }

    public void saveLastEpisodes(String category){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(LAST_CATEGORY, category);
        editor.apply();
    }

    public void saveLastPlayedArtist(String artist){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(LAST_ARTIST, artist);
        editor.apply();
    }

}


















