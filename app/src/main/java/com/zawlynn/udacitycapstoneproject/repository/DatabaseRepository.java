package com.zawlynn.udacitycapstoneproject.repository;

import com.zawlynn.udacitycapstoneproject.PodcastApplication;
import com.zawlynn.udacitycapstoneproject.data.database.dao.CuratedPodcastDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.EpisodeDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.GenreDao;
import com.zawlynn.udacitycapstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class DatabaseRepository {
    @Inject
    GenreDao genreDao;
    @Inject
    EpisodeDao episodeDao;
    @Inject
    CuratedPodcastDao curatedPodcastDao;
    private static DatabaseRepository instance;

    public static DatabaseRepository getInstance() {
        if (instance == null) {
            instance = new DatabaseRepository();
        }
        return instance;
    }

    private DatabaseRepository() {
        PodcastApplication.getInstance().getArticleComponent().inject(this);
    }

    public Flowable<List<Genre>> getGenres() {
        return genreDao.getAllGenres();
    }

    public Flowable<List<Episode>> getRecommendedEpisode() {
        return episodeDao.getAllEpisode();
    }
    public Flowable<List<CuratedPodcast>> getAllCuratedPodcast() {
        return curatedPodcastDao.getAllCuratedPodcast();
    }
}
