package com.zawlynn.udacitycapstoneproject.ui.main.adapter.podcast;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.zawlynn.udacitycapstoneproject.databinding.BindingBestPodcastItem;
import com.zawlynn.udacitycapstoneproject.databinding.BindingEpisodeItem;
import com.zawlynn.udacitycapstoneproject.databinding.BindingPodcastItem;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;
import com.zawlynn.udacitycapstoneproject.ui.main.event.OnPodcastClick;

public class PodcastAdapter extends ListAdapter<Podcast, PodcastViewHolder> {
    public OnPodcastClick podcastClick;
    public PodcastAdapter(OnPodcastClick onPodcastClick) {
        super(new PodcastItemCallback());
        podcastClick=onPodcastClick;
    }
    @NonNull
    @Override
    public PodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BindingBestPodcastItem itemBinding = BindingBestPodcastItem.inflate(layoutInflater, parent, false);
        return new PodcastViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastViewHolder holder, int position) {
        Podcast item=getItem(position);
        holder.bind(item,podcastClick);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
