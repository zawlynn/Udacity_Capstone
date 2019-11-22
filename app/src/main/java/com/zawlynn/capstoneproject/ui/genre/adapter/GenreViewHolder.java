package com.zawlynn.capstoneproject.ui.genre.adapter;


import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zawlynn.capstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.R;
import com.zawlynn.udacitycapstoneproject.databinding.GenreItemBinding;
import com.zawlynn.capstoneproject.ui.genre.event.OnGenreClicked;


class GenreViewHolder extends RecyclerView.ViewHolder {
    private GenreItemBinding itemBinding;
    private Context context;
    private OnGenreClicked onGenreClicked;
    private boolean isSearch;
    GenreViewHolder(GenreItemBinding binding,Context context,OnGenreClicked onGenreClicked,boolean search) {
        super(binding.getRoot());
        this.onGenreClicked=onGenreClicked;
        this.itemBinding = binding;
        this.context=context;
        isSearch=search;
    }
    void bind(Genre genre) {
        itemBinding.tvBackground.setOnClickListener(view -> {
            if(!isSearch) {
                if (genre.isSelected()) {
                    genre.setSelected(false);
                    changeColor(genre);
                } else {
                    genre.setSelected(true);
                    changeColor(genre);
                }
            }
            onGenreClicked.onGenreClicked(genre);
        });
        if(!isSearch) {
            changeColor(genre);
        }
        itemBinding.tvGenre.setText(genre.getName());
    }
    private void changeColor(Genre genre){
        if(genre.isSelected()){
            itemBinding.tvBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.grey));

        }else {
            itemBinding.tvBackground.setBackground(ContextCompat.getDrawable(context,R.drawable.genre_background));
        }
    }
}
