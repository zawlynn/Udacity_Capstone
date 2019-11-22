package com.zawlynn.udacitycapstoneproject.ui.main.adapter.episode;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zawlynn.udacitycapstoneproject.databinding.BindingEpisodeItem;
import com.zawlynn.udacitycapstoneproject.databinding.GenreItemBinding;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.ui.podcast.event.OnEpisodeClick;


class EpisodeViewHolder extends RecyclerView.ViewHolder {
    private BindingEpisodeItem itemBinding;
    private Context context;
    EpisodeViewHolder(BindingEpisodeItem binding) {
        super(binding.getRoot());
        this.itemBinding = binding;
        context = binding.getRoot().getContext();
    }

    void bind(Episode item) {
        itemBinding.tvEpisodeTitle.setText(item.getTitle());
        Glide.with(context)
                .load(item.getThumbnail())
                .apply(RequestOptions.circleCropTransform())
                .into(itemBinding.imgEpisodeThumb);


    }
}
