package com.zawlynn.udacitycapstoneproject.ui.main.adapter.episode;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;


public class EpisodeItemCallback extends  DiffUtil.ItemCallback<Episode>{
    public boolean areItemsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
        return oldItem.equals(newItem);
    }
}
