package com.zawlynn.capstoneproject.ui.main.adapter.curated;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zawlynn.capstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.databinding.BindingPodcastItem;
import com.zawlynn.capstoneproject.ui.main.fragment.OnCuratedPodcastClicked;


class CuratedPodcastViewHolder extends RecyclerView.ViewHolder {
    private BindingPodcastItem itemBinding;
    private Context context;
    private OnCuratedPodcastClicked onCuratedPodcastClicked;
    CuratedPodcastViewHolder(BindingPodcastItem binding, OnCuratedPodcastClicked  podcastClicked) {
        super(binding.getRoot());
        this.itemBinding = binding;
        context = binding.getRoot().getContext();
        this.onCuratedPodcastClicked=podcastClicked;
    }

    void bind(CuratedPodcast item) {
        itemBinding.tvPodcastTitle.setText(item.getTitle());
        if(item.getPodcasts().size()>0){
            Glide.with(context)
                    .load(item.getPodcasts().get(0).getThumbnail())
                    .into(itemBinding.imgPodcastThumb);
        }
        itemBinding.imgPodcastThumb.setOnClickListener(view -> {
            this.onCuratedPodcastClicked.onCuratedPodcastClicked(item);
        });
    }
}
