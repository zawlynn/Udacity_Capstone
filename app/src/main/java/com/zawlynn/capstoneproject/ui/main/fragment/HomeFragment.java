package com.zawlynn.capstoneproject.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zawlynn.capstoneproject.pojo.CuratedPodcast;
import com.zawlynn.capstoneproject.pojo.Genre;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.ui.main.adapter.episode.EpisodeAdapter;
import com.zawlynn.capstoneproject.ui.main.adapter.podcast.PodcastAdapter;
import com.zawlynn.capstoneproject.ui.podcast.PodcastDetailsActivity;
import com.zawlynn.capstoneproject.ui.search.SearchActivity;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingHomeFrag;
import com.zawlynn.capstoneproject.ui.genre.adapter.GenreAdapter;
import com.zawlynn.capstoneproject.ui.genre.event.OnGenreClicked;
import com.zawlynn.capstoneproject.ui.main.adapter.curated.CuratedPodcastAdapter;
import com.zawlynn.capstoneproject.ui.main.event.OnNetworkStateListener;
import com.zawlynn.capstoneproject.ui.main.event.OnPodcastClick;
import com.zawlynn.capstoneproject.ui.main.viewmodel.MainViewmodel;
import com.zawlynn.capstoneproject.ui.podcast.PodcastActivity;
import com.zawlynn.capstoneproject.ui.podcast.event.OnEpisodeClick;
import com.zawlynn.capstoneproject.utils.Constant;
import com.zawlynn.capstoneproject.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.UUID;

public class HomeFragment extends Fragment implements OnPodcastClick, OnGenreClicked,
        OnCuratedPodcastClicked , OnEpisodeClick {
    private OnNetworkStateListener onNetworkStateListener;
    private MainViewmodel viewmodel;
    private BindingHomeFrag binding;
    private EpisodeAdapter episodeAdapter;
    private GenreAdapter genreAdapter;
    private CuratedPodcastAdapter curatedPodcastAdapter;
    private PodcastAdapter podcastAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initUI();
        return binding.getRoot();
    }
    private void initUI(){
        binding.recRecommendations.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),
                RecyclerView.HORIZONTAL,false));
        episodeAdapter=new EpisodeAdapter(this);
        binding.recRecommendations.setAdapter(episodeAdapter);

        binding.recGenre.setLayoutManager( new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));
        genreAdapter=new GenreAdapter(this,true);
        binding.recGenre.setAdapter(genreAdapter);

        binding.recCuratedPodcast.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),
                2,RecyclerView.HORIZONTAL,false));
        curatedPodcastAdapter=new CuratedPodcastAdapter(this);
        binding.recCuratedPodcast.setAdapter(curatedPodcastAdapter);

        binding.recNewPodcast.setLayoutManager( new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        podcastAdapter=new PodcastAdapter(this);
        binding.recNewPodcast.setAdapter(podcastAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewmodel = ViewModelProviders.of(this).get(MainViewmodel.class);
        viewmodel.getGenre();
        viewmodel.getAllCuratePocast();
        viewmodel.getRecommendedEpisode();
        viewmodel.getRecommended_episodes().observe(this,episodes -> {
            episodeAdapter.submitList(episodes);
        });
        viewmodel.getGenresLiveData().observe(this,genres -> {
            genreAdapter.submitList(genres);
        });
        viewmodel.getCuratepodcasts().observe(this,curatedPodcasts -> {
            curatedPodcastAdapter.submitList(curatedPodcasts);
        });
        viewmodel.getPodcasts().observe(this,podcasts -> {
            podcastAdapter.submitList(podcasts);
        });
        if(NetworkUtils.getInstance().isNetworkStatusAvailable(getContext())){
            viewmodel.getBestPodcast();
        }else {
            onNetworkStateListener.isNetworkAvailable(false);
        }

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

    @Override
    public void onGenreClicked(Genre genre) {
        Intent i=new Intent(getContext(), SearchActivity.class);
        i.putExtra(Constant.DATA,genre);
        startActivity(i);
    }

    @Override
    public void onCuratedPodcastClicked(CuratedPodcast curatedPodcast) {
        Intent i=new Intent(getContext(), PodcastDetailsActivity.class);
        i.putExtra(Constant.DATA,curatedPodcast);
        startActivity(i);
    }

    @Override
    public void onEpisodeClick(int id) {
        Intent intent=new Intent(getContext(), PodcastActivity.class);
        intent.putParcelableArrayListExtra(Constant.EPISODES, (ArrayList<? extends Parcelable>)
                viewmodel.getRecommended_episodes().getValue());
        Podcast podcast=new Podcast();
        podcast.setTitle(getResources().getString(R.string.recommendations));
        podcast.setId(UUID.randomUUID().toString());
        podcast.setImage(viewmodel.getRecommended_episodes().getValue().get(0).getImage());
        intent.putExtra(Constant.DATA,podcast);
        startActivity(intent);
    }
}
