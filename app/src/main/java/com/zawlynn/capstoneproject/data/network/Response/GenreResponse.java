package com.zawlynn.capstoneproject.data.network.Response;

import com.zawlynn.capstoneproject.pojo.Genre;

import java.util.List;

public class GenreResponse {
    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
