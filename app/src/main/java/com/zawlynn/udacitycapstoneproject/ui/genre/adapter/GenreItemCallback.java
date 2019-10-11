package com.zawlynn.udacitycapstoneproject.ui.genre.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.zawlynn.udacitycapstoneproject.pojo.Genre;


public class GenreItemCallback extends  DiffUtil.ItemCallback<Genre>{
    public boolean areItemsTheSame(@NonNull Genre oldItem, @NonNull Genre newItem) {
        return oldItem.getName().equals(newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Genre oldItem, @NonNull Genre newItem) {
        return oldItem.getId().equals(newItem.getId());
    }
}
