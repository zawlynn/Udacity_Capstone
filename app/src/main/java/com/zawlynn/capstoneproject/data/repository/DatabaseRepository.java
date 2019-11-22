package com.zawlynn.capstoneproject.data.repository;

import com.zawlynn.capstoneproject.PodcastApplication;
import com.zawlynn.capstoneproject.data.database.dao.CuratedPodcastDao;
import com.zawlynn.capstoneproject.data.database.dao.EpisodeDao;
import com.zawlynn.capstoneproject.data.database.dao.GenreDao;
import com.zawlynn.capstoneproject.data.database.dao.PodcastDao;
import com.zawlynn.capstoneproject.pojo.CuratedPodcast;
import com.zawlynn.capstoneproject.pojo.Genre;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.pojo.Episode;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class DatabaseRepository {
    @Inject
    GenreDao genreDao;
    @Inject
    EpisodeDao episodeDao;
    @Inject
    CuratedPodcastDao curatedPodcastDao;
    @Inject
    PodcastDao podcastDao;
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

    public Flowable<List<Episode>> getSavedEpisode() {
        return episodeDao.getSavedEpisode();
    }

    public Flowable<List<CuratedPodcast>> getAllCuratedPodcast() {
        return curatedPodcastDao.getAllCuratedPodcast();
    }

    public void saveRemoveEpisode(Episode episode) {
        episodeDao.saveEpisode(episode);
    }

    public void saveRemovePodcast(Podcast podcast, Boolean exist) {
        if (exist) {
            podcastDao.deletePodcast(podcast.getId());
        } else {
            podcastDao.savePodcast(podcast);
        }

    }
    public void saveRemoveGenre(Genre genre){
        genreDao.saveGenre(genre);
    }

    public void removedEpisode(Episode episode) {
        episodeDao.removeEpisode(episode.getId());
    }

    public Observable<String> checkexits(String id) {
        return podcastDao.checkExists(id);
    }
    public Observable<String> checkDownloaded(String id) {
        return episodeDao.checkExists(id);
    }
    public Flowable<List<Podcast>> getSavedPodcasts() {
        return podcastDao.getAllPodcast();
    }
}
