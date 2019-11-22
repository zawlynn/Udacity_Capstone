package com.zawlynn.udacitycapstoneproject.ui.genre.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.data.repository.DatabaseRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenreViewModel extends AndroidViewModel  {
    private DatabaseRepository repository;
    private Disposable disposable = new CompositeDisposable();
    private MutableLiveData<List<Genre>> genres = new MutableLiveData<>();
    public GenreViewModel(@NonNull Application application) {
        super(application);
        repository = DatabaseRepository.getInstance();
        getGenres();
    }

    private void getGenres() {
        disposable = repository.getGenres().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(values ->
                        genres.postValue(values)
                );
    }

    public void onGenreStatus(Genre genre) {
        disposable = Completable.fromAction(() -> {
            repository.saveRemoveGenre(genre);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    public LiveData<List<Genre>> getGenresLiveData() {
        return genres;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
