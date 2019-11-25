package com.zawlynn.capstoneproject.ui.podcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zawlynn.capstoneproject.media.MediaLibrary;
import com.zawlynn.capstoneproject.media.client.MediaBrowserHelper;
import com.zawlynn.capstoneproject.media.client.MediaBrowserHelperCallback;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.service.MediaService;
import com.zawlynn.capstoneproject.ui.main.event.OnNetworkStateListener;
import com.zawlynn.capstoneproject.ui.podcast.event.IPodcastListener;
import com.zawlynn.capstoneproject.ui.podcast.fragment.MediaControllerFragment;
import com.zawlynn.capstoneproject.ui.podcast.fragment.PlaylistFragment;
import com.zawlynn.capstoneproject.utils.MyPreferenceManager;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.PodcastBinding;
import com.zawlynn.capstoneproject.pojo.Episode;
import com.zawlynn.capstoneproject.ui.podcast.fragment.PodcastFragment;
import com.zawlynn.capstoneproject.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class PodcastActivity extends AppCompatActivity implements IPodcastListener,
        MediaBrowserHelperCallback, OnNetworkStateListener {
    PodcastBinding binding;
    Podcast podcast;
    private MediaBrowserHelper mMediaBrowserHelper;
    private MyPreferenceManager mMyPrefManager;
    private boolean mIsPlaying;
    private SeekBarBroadcastReceiver mSeekbarBroadcastReceiver;
    private UpdateUIBroadcastReceiver mUpdateUIBroadcastReceiver;
    private boolean mOnAppOpen;
    private boolean mWasConfigurationChange = false;
    private static final String TAG = "PodcastActivity_";
    List<Episode> episodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_podcast);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        podcast = getIntent().getExtras().getParcelable(Constant.DATA);
        binding.toolbar.setTitle(podcast.getTitle());
        episodes=getIntent().getExtras().getParcelableArrayList(Constant.EPISODES);
        mMyPrefManager = new MyPreferenceManager(this);
        mMediaBrowserHelper = new MediaBrowserHelper(this, MediaService.class);
        mMediaBrowserHelper.setMediaBrowserHelperCallback(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, PodcastFragment.newInstance(podcast,episodes), getString(R.string.fragment_podcast));
        transaction.commit();
    }

    private class UpdateUIBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMediaId = intent.getStringExtra(getString(R.string.broadcast_new_media_id));
            Log.d(TAG, "onReceive: CALLED: " + newMediaId);
            if (getPlaylistFragment() != null) {
                Log.d(TAG, "onReceive: " + MediaLibrary.getInstance().getMediaItem(newMediaId)
                        .getDescription().getMediaId());
                getPlaylistFragment().updateUI(MediaLibrary.getInstance().getMediaItem(newMediaId));
            }
        }
    }

    private void initUpdateUIBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.broadcast_update_ui));
        mUpdateUIBroadcastReceiver = new UpdateUIBroadcastReceiver();
        registerReceiver(mUpdateUIBroadcastReceiver, intentFilter);
    }

    @Override
    public void onMediaControllerConnected(MediaControllerCompat mediaController) {
        getMediaControllerFragment().getMediaSeekBar().setMediaController(mediaController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSeekBarBroadcastReceiver();
        initUpdateUIBroadcastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSeekbarBroadcastReceiver != null) {
            unregisterReceiver(mSeekbarBroadcastReceiver);
        }
        if (mUpdateUIBroadcastReceiver != null) {
            unregisterReceiver(mUpdateUIBroadcastReceiver);
        }
    }

    private class SeekBarBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long seekProgress = intent.getLongExtra(Constant.SEEK_BAR_PROGRESS, 0);
            long seekMax = intent.getLongExtra(Constant.SEEK_BAR_MAX, 0);
            if (!getMediaControllerFragment().getMediaSeekBar().isTracking()) {
                getMediaControllerFragment().getMediaSeekBar().setProgress((int) seekProgress);
                getMediaControllerFragment().getMediaSeekBar().setMax((int) seekMax);
            }
        }
    }

    private void initSeekBarBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.broadcast_seekbar_update));
        mSeekbarBroadcastReceiver = new SeekBarBroadcastReceiver();
        registerReceiver(mSeekbarBroadcastReceiver, intentFilter);
    }


    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        Log.d(TAG, "onMetadataChanged: called");
        if (metadata == null) {
            return;
        }
        getMediaControllerFragment().setMediaTitle(metadata);
    }

    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat state) {
        Log.d(TAG, "onPlaybackStateChanged: called.");
        mIsPlaying = state != null &&
                state.getState() == PlaybackStateCompat.STATE_PLAYING;

        if (getMediaControllerFragment() != null) {
            getMediaControllerFragment().setIsPlaying(mIsPlaying);
        }
    }

    private PlaylistFragment getPlaylistFragment() {
        PlaylistFragment playlistFragment = (PlaylistFragment) getSupportFragmentManager()
                .findFragmentByTag(getString(R.string.fragment_playlist));
        if (playlistFragment != null) {
            return playlistFragment;
        }
        return null;
    }

    private MediaControllerFragment getMediaControllerFragment() {
        MediaControllerFragment mediaControllerFragment = (MediaControllerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bottom_media_controller);
        if (mediaControllerFragment != null) {
            return mediaControllerFragment;
        }
        return null;
    }

    @Override
    public void onMediaSelected(String playlistId, MediaMetadataCompat mediaItem, int queuePosition) {
        if (mediaItem != null) {
            Log.d(TAG, "onMediaSelected: CALLED: " + mediaItem.getDescription().getMediaId());
            String currentPlaylistId = getMyPreferenceManager().getPlaylistId();
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.MEDIA_QUEUE_POSITION, queuePosition);
            if (playlistId.equals(currentPlaylistId)) {
                mMediaBrowserHelper.getTransportControls().playFromMediaId(mediaItem.getDescription()
                        .getMediaId(), bundle);
            } else {
                bundle.putBoolean(Constant.QUEUE_NEW_PLAYLIST, true); // let the player know this is a new playlist
                mMediaBrowserHelper.subscribeToNewPlaylist(currentPlaylistId, playlistId);
                mMediaBrowserHelper.getTransportControls().playFromMediaId(mediaItem.getDescription()
                        .getMediaId(), bundle);
            }
            mMyPrefManager.updateSharedPreference(this,episodes.get(queuePosition),podcast.getTitle());
            MediaLibrary.sendBroadcastToWidget(getApplicationContext());
            mOnAppOpen = true;
        } else {
            Toast.makeText(this, "select something to play", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public MyPreferenceManager getMyPreferenceManager() {
        return mMyPrefManager;
    }

    @Override
    public void onPlayAudio(int id) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, PlaylistFragment.newInstance(podcast, episodes,false),
                getString(R.string.fragment_playlist));
        transaction.commit();
    }


    @Override
    public void playPause() {
        Log.d(TAG, "playPause: CLICKED");
        if (mOnAppOpen) {
            if (mIsPlaying) {
                mMediaBrowserHelper.getTransportControls().pause();
            } else {
                mMediaBrowserHelper.getTransportControls().play();
            }
        } else {
            if (!getMyPreferenceManager().getPlaylistId().equals("")) {
                onMediaSelected(getMyPreferenceManager().getPlaylistId(),
                        MediaLibrary.getInstance().getMediaItem(getMyPreferenceManager().getLastPlayedMedia()),
                        getMyPreferenceManager().getQueuePosition());
            } else {
                Toast.makeText(this, "select something to play", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setEpisode(List<Episode> episode) {
        episodes = episode;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mWasConfigurationChange = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!getMyPreferenceManager().getPlaylistId().equals("")) {
            prepareLastPlayedMedia();
        } else {
            mMediaBrowserHelper.onStart(mWasConfigurationChange);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called.");
        mMediaBrowserHelper.onStop();
        getMediaControllerFragment().getMediaSeekBar().disconnectController();
    }


    private void prepareLastPlayedMedia() {

        final List<MediaMetadataCompat> mediaItems = new ArrayList<>();
        List<Episode> data = new Gson().fromJson(mMyPrefManager.getLastEpisode(), new TypeToken<List<Episode>>() {
        }.getType());
        for (Episode model : data) {
            mediaItems.add(addToMediaList(model));
        }
        onFinishedGettingPreviousSessionData(mediaItems);

    }

    private void onFinishedGettingPreviousSessionData(List<MediaMetadataCompat> mediaItems) {
        MediaLibrary.getInstance().setMediaItems(mediaItems);
        mMediaBrowserHelper.onStart(mWasConfigurationChange);
    }

    private MediaMetadataCompat addToMediaList(Episode data) {
        MediaMetadataCompat media = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, data.getId())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, data.getTitle())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, data.getAudio())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, data.getDescription())
                .putString(MediaMetadataCompat.METADATA_KEY_DATE, data.getPub_date_ms())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, data.getImage())
                .build();

        return media;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    @Override
    public void isNetworkAvailable(boolean staus) {
        Snackbar.make(binding.container,getResources().getString(R.string.no_internet),
                Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
