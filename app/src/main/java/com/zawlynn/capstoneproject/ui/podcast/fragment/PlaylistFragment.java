
package com.zawlynn.capstoneproject.ui.podcast.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v4.media.MediaMetadataCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.zawlynn.capstoneproject.media.MediaLibrary;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.service.DownloadService;
import com.zawlynn.capstoneproject.ui.main.event.OnNetworkStateListener;
import com.zawlynn.capstoneproject.ui.player.event.OnEpisodeDownload;
import com.zawlynn.capstoneproject.ui.player.viewmodel.PlayerViewmodel;
import com.zawlynn.capstoneproject.ui.podcast.event.IPodcastListener;
import com.zawlynn.capstoneproject.utils.NetworkUtils;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.PlayerBindingFrag;
import com.zawlynn.capstoneproject.pojo.Episode;
import com.zawlynn.capstoneproject.ui.podcast.adapter.EpisodeItemAdapter;
import com.zawlynn.capstoneproject.ui.podcast.event.OnEpisodeClick;
import com.zawlynn.capstoneproject.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class PlaylistFragment extends Fragment implements OnEpisodeClick, OnEpisodeDownload {
    private PlayerViewmodel viewmodel;
    private static final String TAG = "PlaylistFragment";
    private EpisodeItemAdapter mAdapter;
    private OnNetworkStateListener onNetworkStateListener;
    private ArrayList<MediaMetadataCompat> mMediaList = new ArrayList<>();
    private IPodcastListener mIMainActivity;
    private Podcast podcast;
    private List<Episode> episodes;
    private MediaMetadataCompat mSelectedMedia;
    private PlayerBindingFrag binding;
    private boolean isOffline = true;

    public static PlaylistFragment newInstance(Podcast podcast, List<Episode> episodeList, boolean isOffline) {
        PlaylistFragment playlistFragment = new PlaylistFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.DATA, podcast);
        args.putBoolean(Constant.OFFLINE, isOffline);
        args.putParcelableArrayList(Constant.EPISODES, (ArrayList<? extends Parcelable>) episodeList);
        playlistFragment.setArguments(args);
        return playlistFragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            mIMainActivity.setActionBarTitle(podcast.getTitle());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            podcast = getArguments().getParcelable(Constant.DATA);
            episodes = getArguments().getParcelableArrayList(Constant.EPISODES);
            isOffline = getArguments().getBoolean(Constant.OFFLINE);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player, container, false);
        initUI();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mIMainActivity.setActionBarTitle(podcast.getTitle());
        if (savedInstanceState != null) {
            mAdapter.setSelectedIndex(savedInstanceState.getInt("selected_index"));
        }
    }

    private void getSelectedMediaItem(String mediaId) {
        for (MediaMetadataCompat mediaItem : mMediaList) {
            if (mediaItem.getDescription().getMediaId().equals(mediaId)) {
                mSelectedMedia = mediaItem;
                mAdapter.setSelectedIndex(getIndexOfItem(mSelectedMedia));
                break;
            }
        }
    }

    private void retrieveMedia() {
        for (Episode model : episodes) {
            addToMediaList(model);
        }
        updateDataSet();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewmodel = ViewModelProviders.of(this).get(PlayerViewmodel.class);
    }

    private void addToMediaList(Episode data) {
        MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, data.getId())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, data.getTitle())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, data.getAudio())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, data.getDescription())
                .putString(MediaMetadataCompat.METADATA_KEY_DATE, data.getPub_date_ms())
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, data.getImage());
        if (isOffline) {
            builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, data.getLocal_file_cache());
        } else {
            builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, data.getAudio());
        }

        mMediaList.add(builder.build());
    }

    private void updateDataSet() {
        mAdapter.notifyDataSetChanged();
        if (mIMainActivity.getMyPreferenceManager().getLastPlayedArtist().equals(podcast.getId())) {
            getSelectedMediaItem(mIMainActivity.getMyPreferenceManager().getLastPlayedMedia());
        }
        mIMainActivity.getMyPreferenceManager().saveLastEpisodes(new Gson().toJson(episodes));
    }

    private void initUI() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new EpisodeItemAdapter(this, this);
        binding.recyclerView.setAdapter(mAdapter);
        mAdapter.submitList(episodes);
        if (mMediaList.size() == 0) {
            retrieveMedia();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIMainActivity = (IPodcastListener) getActivity();
        onNetworkStateListener = (OnNetworkStateListener) getActivity();

    }

    public void updateUI(MediaMetadataCompat mediaItem) {
        mAdapter.setSelectedIndex(getIndexOfItem(mediaItem));
        mSelectedMedia = mediaItem;
        saveLastPlayedSongProperties();
    }

    private void saveLastPlayedSongProperties() {
        mIMainActivity.getMyPreferenceManager().savePlaylistId(podcast.getId());
        mIMainActivity.getMyPreferenceManager().saveLastPlayedArtist(podcast.getId());
        mIMainActivity.getMyPreferenceManager().saveLastPlayedArtistImage(podcast.getImage());
        mIMainActivity.getMyPreferenceManager().saveLastPlayedMedia(mSelectedMedia.getDescription().getMediaId());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected_index", mAdapter.getSelectedIndex());
    }

    @Override
    public void onEpisodeDownload(Episode episode) {
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.putExtra(Constant.DATA, episode);
        getContext().startService(intent);
    }

    public int getIndexOfItem(MediaMetadataCompat mediaItem) {
        for (int i = 0; i < mMediaList.size(); i++) {
            if (mMediaList.get(i).getDescription().getMediaId()
                    .equals(mediaItem.getDescription().getMediaId())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onEpisodeClick(int position) {
        if (NetworkUtils.getInstance().isNetworkStatusAvailable(getContext())||isOffline) {
            MediaLibrary.getInstance().setMediaItems(mMediaList);
            mSelectedMedia = mMediaList.get(position);
            mAdapter.setSelectedIndex(position);
            mIMainActivity.onMediaSelected(podcast.getId(), mMediaList.get(position), position);
            saveLastPlayedSongProperties();
        } else {
            onNetworkStateListener.isNetworkAvailable(false);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(reciverDownload,
                new IntentFilter(PlaylistFragment.class.getName()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext())
                .unregisterReceiver(reciverDownload);
    }

    private BroadcastReceiver reciverDownload = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean status = intent.getBooleanExtra(Constant.COMPLETED, false);
            if (status) {
                Episode item = intent.getParcelableExtra(Constant.DATA);
                viewmodel.saveRemoveEpisode(item);
                Snackbar.make(binding.container, getResources()
                        .getText(R.string.download_completed), Snackbar.LENGTH_SHORT).show();
            } else {
                mAdapter.notifyDataSetChanged();
                Snackbar.make(binding.container, getResources()
                        .getText(R.string.download_failed), Snackbar.LENGTH_SHORT).show();
            }
        }
    };

}















