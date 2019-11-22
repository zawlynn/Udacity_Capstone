package com.zawlynn.capstoneproject.ui.search.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.zawlynn.capstoneproject.data.repository.ApiRepository;
import com.zawlynn.capstoneproject.pojo.Podcast;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends AndroidViewModel {
    private static final String TAG = "SearchViewModel";
    private Disposable disposable;
    private ApiRepository apiRepository;
    private MutableLiveData<Boolean> loading=new MutableLiveData<>();
    private MutableLiveData<List<Podcast>> searched_podcast = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
        disposable = new CompositeDisposable();
        apiRepository = ApiRepository.getInstance();
    }
    public void searchPodcast(String query) {
        loading.postValue(true);
        disposable = apiRepository.searchPodcast(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    loading.postValue(false);
                    searched_podcast.postValue(value.getPodcasts());
                },e->{
                    loading.postValue(false);
                    Log.d(TAG, "getBestPodcast: "+e.getMessage());
                });
    }
    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }
    public MutableLiveData<List<Podcast>> getSearched_podcast() {
        return searched_podcast;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
