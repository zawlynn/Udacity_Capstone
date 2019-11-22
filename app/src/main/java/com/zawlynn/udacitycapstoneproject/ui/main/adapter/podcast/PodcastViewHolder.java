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
import com.zawlynn.udacitycapstoneproject.ui.main.event.OnPodcastClick;


class PodcastViewHolder extends RecyclerView.ViewHolder {
    private BindingBestPodcastItem itemBinding;
    private Context context;

    PodcastViewHolder(BindingBestPodcastItem binding) {
        super(binding.getRoot());
        this.itemBinding = binding;
        context = binding.getRoot().getContext();
    }

    void bind(Podcast item, OnPodcastClick onPodcastClick) {
        if(item.getTitle()!=null){
            itemBinding.tvPodcastTitle.setText(item.getTitle());
        }else {
            itemBinding.tvPodcastTitle.setText(item.getTitle_original());
        }

        Glide.with(context)
                .load(item.getThumbnail())
                .into(itemBinding.imgPodcastThumb);
        itemBinding.imgPodcastThumb.setOnClickListener(v -> {
            onPodcastClick.onPodcastClick(item);
        });

    }
}
