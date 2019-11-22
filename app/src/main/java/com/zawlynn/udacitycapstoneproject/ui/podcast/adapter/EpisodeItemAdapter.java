package com.zawlynn.udacitycapstoneproject.ui.podcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ListAdapter;

import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.BindingEpisodeItem;
import com.zawlynn.udacitycapstoneproject.databinding.BindingPodcastMetadataItem;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.ui.main.adapter.episode.EpisodeItemCallback;
import com.zawlynn.udacitycapstoneproject.ui.player.event.OnEpisodeDownload;
import com.zawlynn.udacitycapstoneproject.ui.podcast.event.OnEpisodeClick;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class EpisodeItemAdapter extends ListAdapter<Episode, EpisodeItemViewHolder> {
    private OnEpisodeClick onEpisodeClick;
    private OnEpisodeDownload onEpisodeDownload;
    private Disposable disposable=new CompositeDisposable();
    private int mSelectedIndex=-1;
    private Context context;
    public EpisodeItemAdapter(OnEpisodeClick onEpisodeClick, OnEpisodeDownload onEpisodeDownload) {
        super(new EpisodeItemCallback());
        this.onEpisodeClick=onEpisodeClick;
        this.onEpisodeDownload=onEpisodeDownload;
    }
    @NonNull
    @Override
    public EpisodeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        context=parent.getContext();
        BindingPodcastMetadataItem itemBinding = BindingPodcastMetadataItem.inflate(layoutInflater, parent, false);
        return new EpisodeItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeItemViewHolder holder, int position) {
        Episode item=getItem(position);
        holder.bind(item,onEpisodeDownload,disposable);
        holder.itemView.setOnClickListener(v -> {
            onEpisodeClick.onEpisodeClick(position);
        });
        if(position == mSelectedIndex){
            holder.itemBinding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
        else{
            holder.itemBinding.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
        holder.setIsRecyclable(false);
    }
    public void setSelectedIndex(int index){
        mSelectedIndex = index;
        notifyDataSetChanged();
    }

    public int getSelectedIndex(){
        return mSelectedIndex;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void clear(){
        disposable.dispose();
    }

}
