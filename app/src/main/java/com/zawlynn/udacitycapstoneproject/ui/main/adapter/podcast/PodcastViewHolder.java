package com.zawlynn.udacitycapstoneproject.ui.main.adapter.podcast;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zawlynn.udacitycapstoneproject.databinding.BindingBestPodcastItem;
import com.zawlynn.udacitycapstoneproject.databinding.BindingEpisodeItem;
import com.zawlynn.udacitycapstoneproject.databinding.BindingPodcastItem;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;


class PodcastViewHolder extends RecyclerView.ViewHolder {
    private BindingBestPodcastItem itemBinding;
    private Context context;

    PodcastViewHolder(BindingBestPodcastItem binding) {
        super(binding.getRoot());
        this.itemBinding = binding;
        context = binding.getRoot().getContext();
    }

    void bind(Podcast item) {
        itemBinding.tvPodcastTitle.setText(item.getTitle());
        Glide.with(context)
                .load(item.getThumbnail())
                .into(itemBinding.imgPodcastThumb);
    }
}
