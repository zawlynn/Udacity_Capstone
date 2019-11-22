package com.zawlynn.capstoneproject.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zawlynn.capstoneproject.pojo.Episode;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.ui.player.OfflinePlayerActivity;
import com.zawlynn.capstoneproject.ui.player.event.OnEpisodeDownload;
import com.zawlynn.capstoneproject.ui.podcast.adapter.EpisodeItemAdapter;
import com.zawlynn.capstoneproject.ui.podcast.event.OnEpisodeClick;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingLibFrag;
import com.zawlynn.capstoneproject.ui.main.viewmodel.MainViewmodel;
import com.zawlynn.capstoneproject.utils.Constant;

import java.util.ArrayList;
import java.util.UUID;


public class LibraryFragment extends Fragment implements OnEpisodeClick, OnEpisodeDownload {
    private MainViewmodel viewmodel;
    BindingLibFrag binding;
    EpisodeItemAdapter adapter;
    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_library, container, false);
        initUI();
        return binding.getRoot();
    }

    private void initUI() {
        binding.recLibrary.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        adapter = new EpisodeItemAdapter(this,this);
        binding.recLibrary.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewmodel = ViewModelProviders.of(this).get(MainViewmodel.class);
        viewmodel.getSavedEpisode();
        viewmodel.getSaved_episodes().observe(this,episodes -> {
            adapter.submitList(episodes);
        });

    }

    @Override
    public void onEpisodeClick(int id) {
        Intent intent=new Intent(getContext(), OfflinePlayerActivity.class);
        intent.putParcelableArrayListExtra(Constant.EPISODES, (ArrayList<? extends Parcelable>)
                viewmodel.getSaved_episodes().getValue());
        Podcast podcast=new Podcast();
        podcast.setTitle(getResources().getString(R.string.saved_episode));
        podcast.setId(UUID.randomUUID().toString());
        podcast.setImage(viewmodel.getSaved_episodes().getValue().get(0).getImage());
        intent.putExtra(Constant.DATA,podcast);
        startActivity(intent);
    }

    @Override
    public void onEpisodeDownload(Episode id) {
        viewmodel.removeSavedEpisode(id);
    }
}
