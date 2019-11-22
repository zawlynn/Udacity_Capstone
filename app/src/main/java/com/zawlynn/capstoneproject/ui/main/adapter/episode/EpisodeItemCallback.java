package com.zawlynn.capstoneproject.ui.main.adapter.episode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.zawlynn.capstoneproject.pojo.Episode;


public class EpisodeItemCallback extends  DiffUtil.ItemCallback<Episode>{
    public boolean areItemsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
        return oldItem.equals(newItem);
    }
}
