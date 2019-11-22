package com.zawlynn.udacitycapstoneproject.data.network;

import com.zawlynn.udacitycapstoneproject.data.network.Response.BestPodcastResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.CuratedResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.GenreResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.PodcastMetadataResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.RecommendedEpisodesResponse;
import com.zawlynn.udacitycapstoneproject.data.network.Response.SearchPodcastResponse;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("api/v1/genres")
    Flowable<Response<GenreResponse>> getGenres();

    @GET("api/v1/episodes/deecd6ee486f4f47abe61998efc2c0c2/recommendations?safe_mode=1")
    Flowable<Response<RecommendedEpisodesResponse>> getRecommendations();

    @GET("api/v1/curated_podcasts")
    Flowable<Response<CuratedResponse>> getCuratedPodcasts(@Query(value = "page") String page);

    @GET("api/v1/best_podcasts")
    Flowable<BestPodcastResponse> getBestPodcasts(@Query(value = "page") String page);

    @GET("api/v1/typeahead")
    Flowable<SearchPodcastResponse> searchPodcast(@Query(value = "q") String query
            ,@Query(value = "show_podcasts") String show_podcasts
            ,@Query(value = "show_genres") String show_genres );

    @GET("api/v1/podcasts/{id}")
    Flowable<PodcastMetadataResponse> getPodcastMetadata(@Path("id") String id);
}
