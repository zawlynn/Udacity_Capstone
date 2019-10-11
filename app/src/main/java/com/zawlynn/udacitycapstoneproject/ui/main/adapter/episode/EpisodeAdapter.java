package com.zawlynn.udacitycapstoneproject.ui.main.adapter.episode;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.zawlynn.udacitycapstoneproject.databinding.BindingEpisodeItem;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;

public class EpisodeAdapter extends ListAdapter<Episode, EpisodeViewHolder> {
    public EpisodeAdapter() {
        super(new EpisodeItemCallback());
    }
    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BindingEpisodeItem itemBinding = BindingEpisodeItem.inflate(layoutInflater, parent, false);
        return new EpisodeViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode item=getItem(position);
        holder.bind(item);
        holder.setIsRecyclable(false);
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
