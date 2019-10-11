package com.zawlynn.udacitycapstoneproject.ui.login.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.zawlynn.udacitycapstoneproject.data.network.Resource;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.repository.ApiRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends AndroidViewModel {
    private Disposable disposable;
    private static final String TAG = "GenreViewModel_";
    private MutableLiveData<Boolean> success = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<List<Genre>> genre=new MutableLiveData<>();
    public LoginViewModel(@NonNull Application application) {
        super(application);
        disposable = new CompositeDisposable();
        disposable = ApiRepository.getInstance().getGenres(application)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    if (it.status == Resource.Status.SUCCESS) {
                        assert it.data != null;
                        Log.d(TAG, "LoginViewModel: SUCCESS");
                        genre.postValue(it.data);
                    }
                }, e -> {
                    Log.d(TAG, "INFO: "+e.getMessage());
                    success.postValue(false);
                    message.postValue(e.getMessage());
                });
    }
    public MutableLiveData<List<Genre>> getGenre() {
        return genre;
    }
    public MutableLiveData<Boolean> getStatus(){
        return success;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
