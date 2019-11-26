package com.zawlynn.udacitycapstoneproject;

import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.google.gson.Gson;
import com.zawlynn.capstoneproject.PodcastApplication;
import com.zawlynn.capstoneproject.data.network.ApiServices;
import com.zawlynn.capstoneproject.data.repository.ApiRepository;
import com.zawlynn.capstoneproject.di.component.DaggerDataComponent;
import com.zawlynn.capstoneproject.di.component.DataComponent;
import com.zawlynn.capstoneproject.di.module.ApplicationContextModule;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiTest {
    DataComponent component;
    ApiServices apiServices;
    Disposable disposable=new CompositeDisposable();
    private static final String TAG = "ApiTest_";
    @Before
    public void initService(){
       component= DaggerDataComponent.builder()
               .applicationContextModule(new ApplicationContextModule(getContext())).build();
       apiServices=ApiRepository.getInstance().apiServices;
    }

    private PodcastApplication getContext(){
        return (PodcastApplication) InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getApplicationContext();
    }

    @Test
    public void testRecommendations(){
        disposable= apiServices.getRecommendations().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d(TAG, "TestApi: "+new Gson().toJson(response));
                });
    }
    @Test
    public void testCuratedPodcasts(){
        disposable=apiServices.getCuratedPodcasts("1").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    Log.d(TAG, "TestApi: "+new Gson().toJson(response));
                });
    }
    @Test
    public void testGenreApi(){
        disposable=apiServices.getGenres().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    Log.d(TAG, "TestApi: "+new Gson().toJson(response));
                });
    }

}
