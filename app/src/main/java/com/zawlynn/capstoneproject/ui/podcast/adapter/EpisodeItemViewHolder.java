package com.zawlynn.capstoneproject.ui.podcast.adapter;


import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zawlynn.capstoneproject.ui.player.event.OnEpisodeDownload;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.capstoneproject.data.repository.DatabaseRepository;
import com.zawlynn.udacitycapstoneproject.databinding.BindingPodcastMetadataItem;
import com.zawlynn.capstoneproject.pojo.Episode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


class EpisodeItemViewHolder extends RecyclerView.ViewHolder {
    BindingPodcastMetadataItem itemBinding;
    private Context context;
    private static final String TAG = "EpisodeItemViewHolder";

    EpisodeItemViewHolder(BindingPodcastMetadataItem binding) {
        super(binding.getRoot());
        this.itemBinding = binding;
        context = binding.getRoot().getContext();
    }

    void bind(Episode item, OnEpisodeDownload onEpisodeDownload, Disposable disposable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            itemBinding.tvPublisher.setText(Html.fromHtml(item.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            itemBinding.tvPublisher.setText(Html.fromHtml(item.getDescription()));
        }
        itemBinding.tvTitle.setText(item.getTitle());
        itemBinding.progressDownload.setVisibility(View.GONE);
        if (onEpisodeDownload != null) {
            itemBinding.imgDownload.setVisibility(View.VISIBLE);
            itemBinding.imgDownload.setOnClickListener(v -> {
                onEpisodeDownload.onEpisodeDownload(item);
                itemBinding.imgDownload.setVisibility(View.INVISIBLE);
                itemBinding.progressDownload.setVisibility(View.VISIBLE);
            });
            disposable = DatabaseRepository.getInstance().checkDownloaded(item.getId()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        itemBinding.imgDownload.setVisibility(View.VISIBLE);
                        itemBinding.progressDownload.setVisibility(View.GONE);
                        itemBinding.imgDownload.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_remove));
                    }, throwable -> {
                        Log.d(TAG, "bind: " + throwable.getMessage());
                    });
            Glide.with(context)
                    .load(item.getThumbnail())
                    .apply(RequestOptions.circleCropTransform())
                    .into(itemBinding.imgPlay);
        }

    }
}
