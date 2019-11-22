package com.zawlynn.capstoneproject.ui.podcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.PodcastDetailsBinding;
import com.zawlynn.capstoneproject.pojo.CuratedPodcast;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.ui.main.adapter.podcast.PodcastAdapter;
import com.zawlynn.capstoneproject.ui.main.event.OnPodcastClick;
import com.zawlynn.capstoneproject.ui.podcast.viewmodel.PodcastViewmodel;
import com.zawlynn.capstoneproject.utils.Constant;
import com.zawlynn.capstoneproject.utils.NetworkUtils;

public class PodcastDetailsActivity extends AppCompatActivity implements OnPodcastClick {
    PodcastDetailsBinding binding;
    PodcastViewmodel viewmodel;
    CuratedPodcast curatedPodcast;
    PodcastAdapter podcastAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_podcast_details);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        viewmodel= ViewModelProviders.of(this).get(PodcastViewmodel.class);
        curatedPodcast=getIntent().getParcelableExtra(Constant.DATA);
        initUI();
    }
    private void initUI(){
        binding.recPodcastDetails.setLayoutManager( new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        podcastAdapter=new PodcastAdapter(this);
        binding.recPodcastDetails.setAdapter(podcastAdapter);
        podcastAdapter.submitList(curatedPodcast.getPodcasts());
    }

    @Override
    public void onPodcastClick(Podcast podcast) {
        if(NetworkUtils.getInstance().isNetworkStatusAvailable(this)){
            Intent i=new Intent(this, PodcastActivity.class);
            i.putExtra(Constant.DATA,podcast);
            startActivity(i);
        }else {
            Snackbar.make(binding.container,getResources().getString(R.string.no_internet),
                    Snackbar.LENGTH_SHORT).show();
        }
    }
}
