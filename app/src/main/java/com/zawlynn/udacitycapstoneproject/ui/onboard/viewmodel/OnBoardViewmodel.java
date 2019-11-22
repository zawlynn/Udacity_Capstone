package com.zawlynn.udacitycapstoneproject.ui.onboard.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.zawlynn.udacitycapstoneproject.data.network.Resource;
import com.zawlynn.udacitycapstoneproject.data.repository.ApiRepository;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class OnBoardViewmodel extends AndroidViewModel {
    private Disposable disposable;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();
    private MutableLiveData<Boolean> completed = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();
    private static final String TAG = "OnBoardViewmodel";
    public OnBoardViewmodel(@NonNull Application application) {
        super(application);
        disposable = new CompositeDisposable();
        disposable = ApiRepository.getInstance().getRecommendations(application)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    if (it.status == Resource.Status.SUCCESS) {
                        assert it.data != null;
                        Log.d(TAG, "OnBoardViewmodel: SUCCESS "+it.data.size());
                        success.postValue(true);
                    }
                }, e -> {
                    Log.d(TAG, "INFO: "+e.getMessage());
                    success.postValue(false);
                    message.postValue(e.getMessage());
                });
    }
    public void requestCuratedPodcast(Context context){
       disposable= ApiRepository.getInstance().getCuratedPodcast(context,"1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    if (it.status == Resource.Status.SUCCESS) {
                        assert it.data != null;
                        Log.d(TAG, "OnBoardViewmodel: SUCCESS "+it.data.size());
                        completed.postValue(true);
                    }
                }, e -> {
                    Log.d(TAG, "INFO: "+e.getMessage());
                    completed.postValue(false);
                    message.postValue(e.getMessage());
                });
    }
    public MutableLiveData<Boolean> isSuccess(){
        return success;
    }
    public MutableLiveData<Boolean> isComplete(){
        return completed;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
