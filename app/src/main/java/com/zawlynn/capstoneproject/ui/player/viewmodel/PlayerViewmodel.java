package com.zawlynn.capstoneproject.ui.player.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.zawlynn.capstoneproject.data.repository.DatabaseRepository;
import com.zawlynn.capstoneproject.pojo.Episode;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayerViewmodel extends AndroidViewModel {
    private DatabaseRepository databaseRepository;
    private Disposable disposable=new CompositeDisposable();
    private static final String TAG = "PlayerViewmodel";
    private MutableLiveData<List<Episode>> downloaded_eposode=new MutableLiveData<>();
    public PlayerViewmodel(@NonNull Application application) {
        super(application);
        databaseRepository = DatabaseRepository.getInstance();
    }
    public Observable<String> checkDownloaded(String id){
        return databaseRepository.checkDownloaded(id);
    }
    public void saveRemoveEpisode(Episode episode) {
        disposable = Completable.fromAction(() -> {
            databaseRepository.saveRemoveEpisode(episode);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public MutableLiveData<List<Episode>> getDownloaded_eposode() {
        return downloaded_eposode;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
