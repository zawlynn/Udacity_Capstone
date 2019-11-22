package com.zawlynn.capstoneproject.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zawlynn.capstoneproject.ui.main.adapter.podcast.PodcastAdapter;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingLoveFrag;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.ui.main.event.OnNetworkStateListener;
import com.zawlynn.capstoneproject.ui.main.event.OnPodcastClick;
import com.zawlynn.capstoneproject.ui.main.viewmodel.MainViewmodel;
import com.zawlynn.capstoneproject.ui.podcast.PodcastActivity;
import com.zawlynn.capstoneproject.utils.Constant;
import com.zawlynn.capstoneproject.utils.NetworkUtils;


public class FavouriteFragment extends Fragment implements OnPodcastClick {
    private OnNetworkStateListener onNetworkStateListener;
    private MainViewmodel viewmodel;
    private PodcastAdapter podcastAdapter;
    private BindingLoveFrag binding;
    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite, container, false);
        initUI();
        return binding.getRoot();
    }

    private void initUI() {
        binding.recFavourite.setLayoutManager( new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        podcastAdapter=new PodcastAdapter(this);
        binding.recFavourite.setAdapter(podcastAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewmodel = ViewModelProviders.of(this).get(MainViewmodel.class);
        viewmodel.savedPodcasts();
        viewmodel.getSaved_podcasts().observe(this,podcasts -> {
            podcastAdapter.submitList(podcasts);
        });
    }

    @Override
    public void onPodcastClick(Podcast podcast) {
        if(NetworkUtils.getInstance().isNetworkStatusAvailable(getContext())){
            Intent i=new Intent(getContext(), PodcastActivity.class);
            i.putExtra(Constant.DATA,podcast);
            startActivity(i);
        }else {
            onNetworkStateListener.isNetworkAvailable(false);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onNetworkStateListener=(OnNetworkStateListener) getActivity();
    }
}
