package com.zawlynn.udacitycapstoneproject.ui.podcast.viewmodel;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.zawlynn.udacitycapstoneproject.data.network.Response.PodcastMetadataResponse;
import com.zawlynn.udacitycapstoneproject.data.repository.DatabaseRepository;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.data.repository.ApiRepository;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PodcastViewmodel extends AndroidViewModel {
    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private Disposable disposable;
    private MutableLiveData<String> imgUrl = new MutableLiveData<>();
    private MutableLiveData<List<Episode>> episodes = new MutableLiveData<>();
    private static final String TAG = "PodcastViewmodel_";
    private MutableLiveData<Boolean> exist = new MutableLiveData<>();

    public PodcastViewmodel(@NonNull Application application) {
        super(application);
        disposable = new CompositeDisposable();
        apiRepository = ApiRepository.getInstance();
        databaseRepository = DatabaseRepository.getInstance();

    }

    public void getPodcastMetadata(String id) {
        disposable = apiRepository.getPodcastMetadata(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    episodes.postValue(value.getEpisodes());
                    imgUrl.postValue(value.getThumbnail());
                }, e -> {
                    Log.d(TAG, "getBestPodcast: " + e.getMessage());
                });
    }

    public MutableLiveData<String> getImgUrl() {
        return imgUrl;
    }

    public MutableLiveData<List<Episode>> getEpisodes() {
        return episodes;
    }

    public void checkExist(String id) {
        disposable = databaseRepository.checkexits(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    if (value != null) {
                        exist.postValue(true);
                    } else {
                        exist.postValue(false);
                    }
                }, throwable -> {
                    Log.d(TAG, "checkExist: " + throwable.getMessage());
                });
    }

    public void saveRemovePodcast(Podcast podcast, boolean value) {
        disposable = Completable.fromAction(() -> {
            databaseRepository.saveRemovePodcast(podcast, value);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    if (value) {
                        exist.postValue(false);
                    } else {
                        exist.postValue(true);
                    }
                });
    }

    public MutableLiveData<Boolean> getExist() {
        return exist;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    private String loadDummiesData() {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("podcast.json");
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
