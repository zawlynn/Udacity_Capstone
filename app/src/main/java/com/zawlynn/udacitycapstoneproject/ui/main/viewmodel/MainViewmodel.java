package com.zawlynn.udacitycapstoneproject.ui.main.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zawlynn.udacitycapstoneproject.data.network.Response.BestPodcastResponse;
import com.zawlynn.udacitycapstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;
import com.zawlynn.udacitycapstoneproject.data.repository.ApiRepository;
import com.zawlynn.udacitycapstoneproject.data.repository.DatabaseRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainViewmodel extends AndroidViewModel {
    private DatabaseRepository databaseRepository;
    private ApiRepository apiRepository;
    private Disposable disposable;
    private MutableLiveData<List<Episode>> recommended_episodes = new MutableLiveData<>();
    private static final String TAG = "MainViewmodel";
    private MutableLiveData<List<Podcast>> saved_podcasts = new MutableLiveData<>();
    private MutableLiveData<List<Episode>> saved_episodes = new MutableLiveData<>();
    private MutableLiveData<List<Podcast>> best_podcasts = new MutableLiveData<>();
    private MutableLiveData<List<Genre>> genres = new MutableLiveData<>();
    private MutableLiveData<List<CuratedPodcast>> curatepodcasts = new MutableLiveData<>();

    public MainViewmodel(@NonNull Application application) {
        super(application);
        disposable = new CompositeDisposable();
        databaseRepository = DatabaseRepository.getInstance();
        apiRepository = ApiRepository.getInstance();
    }

    public void savedPodcasts() {
        disposable = databaseRepository.getSavedPodcasts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values ->
                        saved_podcasts.postValue(values)
                );
    }

    public void getRecommendedEpisode() {
        disposable = databaseRepository.getRecommendedEpisode().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values ->
                        recommended_episodes.postValue(values)
                );
    }

    public void getGenre() {
        disposable = databaseRepository.getGenres().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values ->
                        genres.postValue(values)
                );
    }

    public void getAllCuratePocast() {
        disposable = databaseRepository.getAllCuratedPodcast().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values ->
                        curatepodcasts.postValue(values)
                );
    }

    public void getBestPodcast() {
        disposable = apiRepository.getBestPodcasts("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    best_podcasts.postValue(value.getChannels());
                },e->{
                    Log.d(TAG, "getBestPodcast: "+e.getMessage());
                });


    }

    public void getSavedEpisode() {
        disposable = databaseRepository.getSavedEpisode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values ->
                        saved_episodes.postValue(values)
                );
    }

    public MutableLiveData<List<Episode>> getSaved_episodes() {
        return saved_episodes;
    }

    public LiveData<List<Episode>> getRecommended_episodes() {
        return recommended_episodes;
    }

    public LiveData<List<Genre>> getGenresLiveData() {
        return genres;
    }

    public MutableLiveData<List<CuratedPodcast>> getCuratepodcasts() {
        return curatepodcasts;
    }

    public MutableLiveData<List<Podcast>> getPodcasts() {
        return best_podcasts;
    }

    public MutableLiveData<List<Podcast>> getSaved_podcasts() {
        return saved_podcasts;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public void removeSavedEpisode(Episode id) {
        disposable = Completable.fromAction(() -> {
            databaseRepository.removedEpisode(id);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private String loadDummiesData() {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("bestpodcasts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
