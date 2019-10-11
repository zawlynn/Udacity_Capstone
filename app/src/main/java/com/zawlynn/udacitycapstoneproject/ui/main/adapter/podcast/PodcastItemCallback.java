package com.zawlynn.udacitycapstoneproject.ui.main.adapter.podcast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;


public class PodcastItemCallback extends  DiffUtil.ItemCallback<Podcast>{
    public boolean areItemsTheSame(@NonNull Podcast oldItem, @NonNull Podcast newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Podcast oldItem, @NonNull Podcast newItem) {
        return oldItem.equals(newItem);
    }
}
