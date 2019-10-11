package com.zawlynn.udacitycapstoneproject.ui.genre.adapter;


import androidx.recyclerview.widget.RecyclerView;

import com.zawlynn.udacitycapstoneproject.databinding.GenreItemBinding;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;


class GenreViewHolder extends RecyclerView.ViewHolder {
    private GenreItemBinding itemBinding;
    GenreViewHolder(GenreItemBinding binding) {
        super(binding.getRoot());
        this.itemBinding = binding;
    }

    void bind(Genre genre) {
            itemBinding.tvGenre.setText(genre.getName());
    }
}
