package com.zawlynn.capstoneproject.media;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;

import com.zawlynn.capstoneproject.ui.widget.PodcastWidgetProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MediaLibrary {

    private static final String TAG = "MediaLibrary";
    private static MediaLibrary instance;
    private List<MediaBrowserCompat.MediaItem> mMediaItems = new ArrayList<>();
    private TreeMap<String, MediaMetadataCompat> mTreeMap = new TreeMap<>();
    private String current_episode_id;
    public static MediaLibrary getInstance(){
        if(instance==null){
           instance=new MediaLibrary();
        }
        return instance;
    }

    public List<MediaBrowserCompat.MediaItem> getMediaItems(){
        return mMediaItems;
    }

    public TreeMap<String, MediaMetadataCompat> getTreeMap(){
        return mTreeMap;
    }

    public void setMediaItems(List<MediaMetadataCompat> mediaItems){
        mMediaItems.clear();
        for(MediaMetadataCompat item: mediaItems){
               mMediaItems.add(
                    new MediaBrowserCompat.MediaItem(
                            item.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
            );
            mTreeMap.put(item.getDescription().getMediaId(), item);
        }
    }

    public MediaMetadataCompat getMediaItem(String mediaId){
        return mTreeMap.get(mediaId);
    }

    public String getCurrent_episode_id() {
        return current_episode_id;
    }

    public void setCurrent_episode_id(String current_episode_id) {
        this.current_episode_id = current_episode_id;
    }
    public static void sendBroadcastToWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, PodcastWidgetProvider.class));

        Intent updateAppWidgetIntent = new Intent();
        updateAppWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateAppWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(updateAppWidgetIntent);
    }
}