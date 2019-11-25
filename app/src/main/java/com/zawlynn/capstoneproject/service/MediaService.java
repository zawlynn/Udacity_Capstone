package com.zawlynn.capstoneproject.service;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.media.MediaBrowserCompat;

import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.media.MediaBrowserServiceCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zawlynn.capstoneproject.media.MediaLibrary;
import com.zawlynn.capstoneproject.media.notifications.MediaNotificationManager;
import com.zawlynn.capstoneproject.media.players.MediaPlayerAdapter;
import com.zawlynn.capstoneproject.media.players.PlaybackInfoListener;
import com.zawlynn.capstoneproject.utils.MyPreferenceManager;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.capstoneproject.utils.Constant;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MediaService extends MediaBrowserServiceCompat {

    private static final String TAG = "MediaService";
    private MediaSessionCompat mSession;
    private MediaPlayerAdapter mPlayback;
    private MyPreferenceManager mMyPrefManager;
    private MediaNotificationManager mMediaNotificationManager;
    private boolean mIsServiceStarted;
    @Override
    public void onCreate() {
        super.onCreate();
        mMyPrefManager = new MyPreferenceManager(this);
        mSession = new MediaSessionCompat(this, TAG);

        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS);
        mSession.setCallback(new MediaSessionCallback());
        setSessionToken(mSession.getSessionToken());

        mPlayback = new MediaPlayerAdapter(this, new MediaPlayerListener());

        mMediaNotificationManager = new MediaNotificationManager(this);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved: stopped");
        super.onTaskRemoved(rootIntent);
        mPlayback.stop();
        stopSelf();
    }

    @Override
    public void onDestroy() {
        mSession.release();
    }


    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String s, int i, @Nullable Bundle bundle) {
        if(s.equals(getApplicationContext().getPackageName())){
            return new BrowserRoot("some_real_playlist", null);
        }
        return new BrowserRoot("empty_media", null);
    }

    @Override
    public void onLoadChildren(@NonNull String s, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        Log.d(TAG, "onLoadChildren: called: " + s + ", " + result);
        if (TextUtils.equals("empty_media", s)) {
            result.sendResult(null);
            return;
        }
        result.sendResult(MediaLibrary.getInstance().getMediaItems());
    }


    public class MediaSessionCallback extends MediaSessionCompat.Callback {

        private final List<MediaSessionCompat.QueueItem> mPlaylist = new ArrayList<>();
        private int mQueueIndex = -1;
        private MediaMetadataCompat mPreparedMedia;

        private void resetPlaylist(){
            mPlaylist.clear();
            mQueueIndex = -1;
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            Log.d(TAG, "onPlayFromMediaId: CALLED.");
            if(extras.getBoolean(Constant.QUEUE_NEW_PLAYLIST, false)){
                resetPlaylist();
            }

            mPreparedMedia = MediaLibrary.getInstance().getTreeMap().get(mediaId);
            mSession.setMetadata(mPreparedMedia);
            if (!mSession.isActive()) {
                mSession.setActive(true);
            }
            mPlayback.playFromMedia(mPreparedMedia);

            int newQueuePosition = extras.getInt(Constant.MEDIA_QUEUE_POSITION, -1);
            if(newQueuePosition == -1){
                mQueueIndex++;
            }
            else{
                mQueueIndex = extras.getInt(Constant.MEDIA_QUEUE_POSITION);
            }
            mMyPrefManager.saveQueuePosition(mQueueIndex);
            mMyPrefManager.saveLastPlayedMedia(mPreparedMedia.getDescription().getMediaId());
        }

        @Override
        public void onAddQueueItem(MediaDescriptionCompat description) {
            Log.d(TAG, "onAddQueueItem: CALLED: position in list: " + mPlaylist.size());
            mPlaylist.add(new MediaSessionCompat.QueueItem(description, description.hashCode()));
            mQueueIndex = (mQueueIndex == -1) ? 0 : mQueueIndex;
            mSession.setQueue(mPlaylist);
        }

        @Override
        public void onRemoveQueueItem(MediaDescriptionCompat description) {
            mPlaylist.remove(new MediaSessionCompat.QueueItem(description, description.hashCode()));
            mQueueIndex = (mPlaylist.isEmpty()) ? -1 : mQueueIndex;
            mSession.setQueue(mPlaylist);
        }

        @Override
        public void onPrepare() {
            if (mQueueIndex < 0 && mPlaylist.isEmpty()) {
                // Nothing to play.
                return;
            }

            String mediaId = mPlaylist.get(mQueueIndex).getDescription().getMediaId();
            mPreparedMedia = MediaLibrary.getInstance().getTreeMap().get(mediaId);
            mSession.setMetadata(mPreparedMedia);

            if (!mSession.isActive()) {
                mSession.setActive(true);
            }
        }

        @Override
        public void onPlay() {

            if (!isReadyToPlay()) {
                return;
            }

            if (mPreparedMedia == null) {
                onPrepare();
            }

            mMyPrefManager.updateEpisodeTitle(getApplicationContext(),mPlaylist.get(mQueueIndex).getDescription().getTitle().toString());
            MediaLibrary.sendBroadcastToWidget(getApplicationContext());

            mPlayback.playFromMedia(mPreparedMedia);
            mMyPrefManager.saveQueuePosition(mQueueIndex);
            mMyPrefManager.saveLastPlayedMedia(mPreparedMedia.getDescription().getMediaId());
        }

        @Override
        public void onPause() {
            mPlayback.pause();
        }

        @Override
        public void onStop() {
            mPlayback.stop();
            mSession.setActive(false);
        }

        @Override
        public void onSkipToNext() {
            Log.d(TAG, "onSkipToNext: SKIP TO NEXT");
            mQueueIndex = (++mQueueIndex % mPlaylist.size());
            mPreparedMedia = null;
            onPlay();
        }

        @Override
        public void onSkipToPrevious() {
            Log.d(TAG, "onSkipToPrevious: SKIP TO PREVIOUS");
            mQueueIndex = mQueueIndex > 0 ? mQueueIndex - 1 : mPlaylist.size() - 1;
            mPreparedMedia = null;
            onPlay();
        }

        @Override
        public void onSeekTo(long pos) {
            mPlayback.seekTo(pos);
        }

        private boolean isReadyToPlay() {
            return (!mPlaylist.isEmpty());
        }
    }


    public class MediaPlayerListener implements PlaybackInfoListener {

        private final ServiceManager mServiceManager;

        MediaPlayerListener() {
            mServiceManager = new ServiceManager();
        }

        @Override
        public void updateUI(String newMediaId) {
            Log.d(TAG, "updateUI: CALLED: " + newMediaId);
            Intent intent = new Intent();
            intent.setAction(getString(R.string.broadcast_update_ui));
            intent.putExtra(getString(R.string.broadcast_new_media_id), newMediaId);
            sendBroadcast(intent);
        }

        @Override
        public void onPlaybackStateChange(PlaybackStateCompat state) {
            // Report the state to the MediaSession.
            mSession.setPlaybackState(state);


            // Manage the started state of this service.
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PLAYING:
                    mServiceManager.updateNotification(
                            state,
                            mPlayback.getCurrentMedia().getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI)
                    );
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    mServiceManager.updateNotification(
                            state,
                            mPlayback.getCurrentMedia().getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI)
                    );
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    Log.d(TAG, "onPlaybackStateChange: STOPPED.");
                    mServiceManager.moveServiceOutOfStartedState();
                    break;
            }
        }

        @Override
        public void onSeekTo(long progress, long max) {
//            Log.d(TAG, "onSeekTo: CALLED: updating seekbar: " + progress + ", max: " + max);
            Intent intent = new Intent();
            intent.setAction(getString(R.string.broadcast_seekbar_update));
            intent.putExtra(Constant.SEEK_BAR_PROGRESS, progress);
            intent.putExtra(Constant.SEEK_BAR_MAX, max);
            sendBroadcast(intent);
        }

        @Override
        public void onPlaybackComplete() {
            Log.d(TAG, "onPlaybackComplete: SKIPPING TO NEXT.");
            mSession.getController().getTransportControls().skipToNext();
        }


        class ServiceManager implements ICallback {

            private String mDisplayImageUri;
            private Bitmap mCurrentArtistBitmap;
            private PlaybackStateCompat mState;
            private GetArtistBitmapAsyncTask mAsyncTask;

            public ServiceManager() {
            }

            public void updateNotification(PlaybackStateCompat state, String displayImageUri){
                mState = state;

                if(!displayImageUri.equals(mDisplayImageUri)){
                    // download new bitmap

                    mAsyncTask = new GetArtistBitmapAsyncTask(
                            Glide.with(MediaService.this)
                            .asBitmap()
                            .load(displayImageUri)
                            .listener(new RequestListener<Bitmap>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            }).submit(), this);

                    mAsyncTask.execute();

                    mDisplayImageUri = displayImageUri;
                }
                else{
                    displayNotification(mCurrentArtistBitmap);
                }
            }

            public void displayNotification(Bitmap bitmap){

                // Manage the started state of this service.
                Notification notification = null;
                switch (mState.getState()) {

                    case PlaybackStateCompat.STATE_PLAYING:
                        notification =
                                mMediaNotificationManager.buildNotification(
                                        mState, getSessionToken(), mPlayback.getCurrentMedia().getDescription(), bitmap);

                        if (!mIsServiceStarted) {
                            ContextCompat.startForegroundService(
                                    MediaService.this,
                                    new Intent(MediaService.this, MediaService.class));
                            mIsServiceStarted = true;
                        }

                        startForeground(MediaNotificationManager.NOTIFICATION_ID, notification);
                        break;

                    case PlaybackStateCompat.STATE_PAUSED:
                        stopForeground(false);
                        notification =
                                mMediaNotificationManager.buildNotification(
                                        mState, getSessionToken(), mPlayback.getCurrentMedia().getDescription(), bitmap);
                        mMediaNotificationManager.getNotificationManager()
                                .notify(MediaNotificationManager.NOTIFICATION_ID, notification);
                        break;
                }
            }

            private void moveServiceOutOfStartedState() {
                stopForeground(true);
                stopSelf();
                mIsServiceStarted = false;
            }

            @Override
            public void done(Bitmap bitmap) {
                mCurrentArtistBitmap = bitmap;
                displayNotification(mCurrentArtistBitmap);
            }
        }

    }

    static class GetArtistBitmapAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        private FutureTarget<Bitmap> mBitmap;
        private ICallback mICallback;


        public GetArtistBitmapAsyncTask(FutureTarget<Bitmap> bitmap, ICallback iCallback) {
            this.mBitmap = bitmap;
            this.mICallback = iCallback;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mICallback.done(bitmap);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {

            try {
                return mBitmap.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}































