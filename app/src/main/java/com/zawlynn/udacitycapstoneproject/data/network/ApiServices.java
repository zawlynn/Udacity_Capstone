package com.zawlynn.udacitycapstoneproject.data.network;

import com.zawlynn.udacitycapstoneproject.data.network.Response.BestPodcastResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.CuratedResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.GenreResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.RecommendationsResponse;
import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("api/v1/genres")
    Flowable<Response<GenreResponse>> getGenres();
    @GET("api/v1/episodes/deecd6ee486f4f47abe61998efc2c0c2/recommendations")
    Flowable<Response<RecommendationsResponse>> getRecommendations();
    @GET("api/v1/curated_podcasts")
    Flowable<Response<CuratedResponse>> getCuratedPodcasts(@Query(value = "page") String page);
    @GET("api/v1/best_podcasts")
    Flowable<BestPodcastResponse> getBestPodcasts(@Query(value = "page") String page);
}
