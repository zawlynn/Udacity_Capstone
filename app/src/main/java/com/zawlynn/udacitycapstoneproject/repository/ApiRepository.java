package com.zawlynn.udacitycapstoneproject.repository;

import android.content.Context;
import android.util.Log;


import com.zawlynn.udacitycapstoneproject.PodcastApplication;
import com.zawlynn.udacitycapstoneproject.data.database.dao.CuratedPodcastDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.EpisodeDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.GenreDao;
import com.zawlynn.udacitycapstoneproject.data.network.ApiServices;
import com.zawlynn.udacitycapstoneproject.data.network.NetworkBoundResource;
import com.zawlynn.udacitycapstoneproject.data.network.Resource;
import com.zawlynn.udacitycapstoneproject.data.network.Response.BestPodcastResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.CuratedResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.GenreResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.RecommendationsResponse;
import com.zawlynn.udacitycapstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import retrofit2.Response;


public class ApiRepository {
    private static final String TAG = "ApiRepository_";
    @Inject
    public ApiServices apiServices;
    @Inject
    public GenreDao genreDao;
    @Inject
    public EpisodeDao episodeDao;
    @Inject
    public CuratedPodcastDao curatedPodcastDao;

    private static ApiRepository instance;

    public static ApiRepository getInstance() {
        if (instance == null) {
            instance = new ApiRepository();
        }
        return instance;
    }

    private ApiRepository() {
        PodcastApplication.getInstance().getArticleComponent()
                .inject(this);
    }

    public Flowable<Resource<List<Genre>>> getGenres(Context context) {
        return new NetworkBoundResource<List<Genre>, GenreResponse>(context) {
            @Override
            protected void saveCallResult(GenreResponse request) {
                Log.d(TAG, "saveCallResult: " + request.getGenres().size());
                genreDao.saveGenres(request.getGenres());
            }

            @Override
            protected Flowable<List<Genre>> loadFromDb() {
                return genreDao.getAllGenres();
            }

            @Override
            protected Flowable<Response<GenreResponse>> createCall() {
                Log.d(TAG, "createCall: done");
                return apiServices.getGenres();
            }
        }.asFlowable();

    }

    public Flowable<Resource<List<Episode>>> getRecommendations(Context context) {
        return new NetworkBoundResource<List<Episode>, RecommendationsResponse>(context) {
            @Override
            protected void saveCallResult(RecommendationsResponse request) {
                episodeDao.saveEpisodes(request.getRecommendations());
            }

            @Override
            protected Flowable<List<Episode>> loadFromDb() {
                return episodeDao.getAllEpisode();
            }

            @Override
            protected Flowable<Response<RecommendationsResponse>> createCall() {
                return apiServices.getRecommendations();
            }
        }.asFlowable();
    }

    public Flowable<Resource<List<CuratedPodcast>>> getCuratedPodcast(Context context, String page) {
        return new NetworkBoundResource<List<CuratedPodcast>, CuratedResponse>(context) {
            @Override
            protected void saveCallResult(CuratedResponse request) {
                curatedPodcastDao.saveCuratedPodcast(request.getCurated_lists());
            }

            @Override
            protected Flowable<List<CuratedPodcast>> loadFromDb() {
                return curatedPodcastDao.getAllCuratedPodcast();
            }

            @Override
            protected Flowable<Response<CuratedResponse>> createCall() {
                return apiServices.getCuratedPodcasts(page);
            }
        }.asFlowable();
    }

    public Flowable<BestPodcastResponse> getBestPodcasts(String page) {
       return apiServices.getBestPodcasts(page);
    }
}

