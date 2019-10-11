package com.zawlynn.udacitycapstoneproject.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.os.Bundle;

import com.linecorp.linesdk.LoginDelegate;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.MainActivityBinding;
import com.zawlynn.udacitycapstoneproject.ui.genre.adapter.GenreAdapter;
import com.zawlynn.udacitycapstoneproject.ui.main.adapter.episode.EpisodeAdapter;
import com.zawlynn.udacitycapstoneproject.ui.main.adapter.curated.CuratedPodcastAdapter;
import com.zawlynn.udacitycapstoneproject.ui.main.adapter.podcast.PodcastAdapter;
import com.zawlynn.udacitycapstoneproject.ui.main.viewmodel.MainViewmodel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_";
    MainActivityBinding binding;
    MainViewmodel viewmodel;
    EpisodeAdapter episodeAdapter;
    GenreAdapter genreAdapter;
    CuratedPodcastAdapter curatedPodcastAdapter;
    PodcastAdapter podcastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewmodel= ViewModelProviders.of(this).get(MainViewmodel.class);
        initUI();
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
        viewmodel.getBestPodcast();
    }
    private void initUI(){
        binding.recRecommendations.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                RecyclerView.HORIZONTAL,false));
        episodeAdapter=new EpisodeAdapter();
        binding.recRecommendations.setAdapter(episodeAdapter);

        binding.recGenre.setLayoutManager( new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.HORIZONTAL));
        genreAdapter=new GenreAdapter();
        binding.recGenre.setAdapter(genreAdapter);

        binding.recCuratedPodcast.setLayoutManager(new GridLayoutManager(getApplicationContext(),
            2,RecyclerView.HORIZONTAL,false));
        curatedPodcastAdapter=new CuratedPodcastAdapter();
        binding.recCuratedPodcast.setAdapter(curatedPodcastAdapter);


        binding.recNewPodcast.setLayoutManager( new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        podcastAdapter=new PodcastAdapter();
        binding.recNewPodcast.setAdapter(podcastAdapter);
    }
}
