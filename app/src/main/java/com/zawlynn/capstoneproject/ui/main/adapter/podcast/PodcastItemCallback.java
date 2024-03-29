package com.zawlynn.capstoneproject.ui.main.adapter.podcast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.zawlynn.capstoneproject.pojo.Podcast;


public class PodcastItemCallback extends  DiffUtil.ItemCallback<Podcast>{
    public boolean areItemsTheSame(@NonNull Podcast oldItem, @NonNull Podcast newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Podcast oldItem, @NonNull Podcast newItem) {
        return oldItem.equals(newItem);
    }
}
