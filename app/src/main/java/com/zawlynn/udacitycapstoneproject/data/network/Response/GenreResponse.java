package com.zawlynn.udacitycapstoneproject.data.network.Response;

import com.zawlynn.udacitycapstoneproject.pojo.Genre;

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
