package com.zawlynn.udacitycapstoneproject.ui.main.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zawlynn.udacitycapstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;
import com.zawlynn.udacitycapstoneproject.repository.ApiRepository;
import com.zawlynn.udacitycapstoneproject.repository.DatabaseRepository;

import java.util.List;

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
    public MutableLiveData<List<Podcast>> getPodcasts() {
        return best_podcasts;
    }

    private MutableLiveData<List<Podcast>> best_podcasts = new MutableLiveData<>();
    private MutableLiveData<List<Genre>> genres = new MutableLiveData<>();
    private MutableLiveData<List<CuratedPodcast>> curatepodcasts = new MutableLiveData<>();

    public MainViewmodel(@NonNull Application application) {
        super(application);
        disposable = new CompositeDisposable();
        databaseRepository = DatabaseRepository.getInstance();
        apiRepository = ApiRepository.getInstance();
        disposable = databaseRepository.getRecommendedEpisode().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values ->
                        recommended_episodes.postValue(values)
                );
        disposable = databaseRepository.getGenres().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values ->
                        genres.postValue(values)
                );
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

    public LiveData<List<Episode>> getRecommended_episodes() {
        return recommended_episodes;
    }

    public LiveData<List<Genre>> getGenresLiveData() {
        return genres;
    }

    public MutableLiveData<List<CuratedPodcast>> getCuratepodcasts() {
        return curatepodcasts;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
