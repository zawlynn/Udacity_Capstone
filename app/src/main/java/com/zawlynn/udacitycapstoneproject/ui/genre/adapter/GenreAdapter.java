package com.zawlynn.udacitycapstoneproject.ui.genre.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.zawlynn.udacitycapstoneproject.databinding.GenreItemBinding;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;

public class GenreAdapter extends ListAdapter<Genre,GenreViewHolder> {
    public GenreAdapter() {
        super(new GenreItemCallback());

    }
    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        GenreItemBinding itemBinding = GenreItemBinding.inflate(layoutInflater, parent, false);
        return new GenreViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Genre item=getItem(position);
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

    public Genre getSelectedItem(int pos){
        return getItem(pos);
    }
}
