package com.zawlynn.capstoneproject.ui.main.adapter.curated;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.zawlynn.capstoneproject.pojo.CuratedPodcast;


public class CuratedPodcastItemCallback extends  DiffUtil.ItemCallback<CuratedPodcast>{
    public boolean areItemsTheSame(@NonNull CuratedPodcast oldItem, @NonNull CuratedPodcast newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame(@NonNull CuratedPodcast oldItem, @NonNull CuratedPodcast newItem) {
        return oldItem.equals(newItem);
    }
}
