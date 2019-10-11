package com.zawlynn.udacitycapstoneproject.ui.main.adapter.curated;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zawlynn.udacitycapstoneproject.databinding.BindingPodcastItem;
import com.zawlynn.udacitycapstoneproject.pojo.CuratedPodcast;


class CuratedPodcastViewHolder extends RecyclerView.ViewHolder {
    private BindingPodcastItem itemBinding;
    private Context context;

    CuratedPodcastViewHolder(BindingPodcastItem binding) {
        super(binding.getRoot());
        this.itemBinding = binding;
        context = binding.getRoot().getContext();
    }

    void bind(CuratedPodcast item) {
        itemBinding.tvPodcastTitle.setText(item.getTitle());
        if(item.getPodcasts().size()>0){
            Glide.with(context)
                    .load(item.getPodcasts().get(0).getThumbnail())
                    .into(itemBinding.imgPodcastThumb);
        }

    }
}
