package com.zawlynn.capstoneproject.ui.main.adapter.curated;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.zawlynn.capstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.databinding.BindingPodcastItem;
import com.zawlynn.capstoneproject.ui.main.fragment.OnCuratedPodcastClicked;


public class CuratedPodcastAdapter extends ListAdapter<CuratedPodcast, CuratedPodcastViewHolder> {
    private OnCuratedPodcastClicked onCuratedPodcastClicked;
    public CuratedPodcastAdapter(OnCuratedPodcastClicked podcastClicked) {
        super(new CuratedPodcastItemCallback());
        this.onCuratedPodcastClicked=podcastClicked;
    }
    @NonNull
    @Override
    public CuratedPodcastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BindingPodcastItem itemBinding = BindingPodcastItem.inflate(layoutInflater, parent, false);
        return new CuratedPodcastViewHolder(itemBinding,this.onCuratedPodcastClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull CuratedPodcastViewHolder holder, int position) {
        CuratedPodcast item=getItem(position);
        holder.bind(item);
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}
