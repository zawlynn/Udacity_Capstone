package com.zawlynn.capstoneproject.ui.podcast.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zawlynn.capstoneproject.pojo.Episode;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.ui.podcast.adapter.EpisodeItemAdapter;
import com.zawlynn.capstoneproject.ui.podcast.event.IPodcastListener;
import com.zawlynn.capstoneproject.ui.podcast.event.OnEpisodeClick;
import com.zawlynn.capstoneproject.ui.podcast.viewmodel.PodcastViewmodel;
import com.zawlynn.capstoneproject.utils.Constant;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingPodcastFrag;

import java.util.ArrayList;
import java.util.List;

public class PodcastFragment extends Fragment implements OnEpisodeClick, View.OnClickListener {

    private BindingPodcastFrag binding;
    private PodcastViewmodel viewmodel;
    private EpisodeItemAdapter adapter;
    private Podcast podcast;
    private Boolean saved=false;
    private IPodcastListener iPodcastListener;
    private List<Episode> episodes;
    public PodcastFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PodcastFragment newInstance(Podcast podcast,List<Episode> episodes) {
        PodcastFragment fragment = new PodcastFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.DATA, podcast);
        args.putParcelableArrayList(Constant.EPISODES, (ArrayList<? extends Parcelable>) episodes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            podcast = getArguments().getParcelable(Constant.DATA);
            episodes = getArguments().getParcelableArrayList(Constant.EPISODES);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_podcast, container, false);
        initUI();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewmodel = ViewModelProviders.of(this).get(PodcastViewmodel.class);
        viewmodel= ViewModelProviders.of(this).get(PodcastViewmodel.class);
        if(episodes==null) {
            viewmodel.getPodcastMetadata(podcast.getId());
        }else {
            adapter.submitList(episodes);
            iPodcastListener.setEpisode(episodes);
        }
        viewmodel.getEpisodes().observe(this,value->{
            adapter.submitList(value);
            iPodcastListener.setEpisode(value);
        });
        viewmodel.getImgUrl().observe(this,url->{
            Glide.with(getContext())
                    .load(url)
                    .into(binding.ivImage);
        });
        viewmodel.getExist().observe(this,aBoolean -> {
            if(aBoolean!=null){
                saved=aBoolean;
                if(saved){
                    binding.imgFavourite.setImageDrawable(ContextCompat
                            .getDrawable(getContext(),R.mipmap.ic_favourited));
                }else {
                    binding.imgFavourite.setImageDrawable(ContextCompat
                            .getDrawable(getContext(),R.mipmap.ic_favourite));
                }
            }
        });
        viewmodel.getEpisodes().getValue();
        viewmodel.checkExist(podcast.getId());
    }
    private void initUI(){
        binding.recPodcast.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,false));
        adapter=new EpisodeItemAdapter(this,null);
        binding.recPodcast.setAdapter(adapter);
        binding.imgFavourite.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iPodcastListener = (IPodcastListener) getActivity();
    }
    @Override
    public void onEpisodeClick(int id) {
        iPodcastListener.onPlayAudio(id);
    }

    @Override
    public void onClick(View v) {
        viewmodel.saveRemovePodcast(podcast,saved);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.clear();
    }
}
