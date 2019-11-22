package com.zawlynn.capstoneproject.ui.main.adapter.podcast;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.udacitycapstoneproject.databinding.BindingBestPodcastItem;
import com.zawlynn.capstoneproject.ui.main.event.OnPodcastClick;


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
