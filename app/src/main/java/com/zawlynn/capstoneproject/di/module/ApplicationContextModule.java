package com.zawlynn.capstoneproject.di.module;

import android.app.Application;

import com.zawlynn.capstoneproject.PodcastApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationContextModule {
    public final PodcastApplication application;

    public ApplicationContextModule(PodcastApplication application){
        this.application=application;
    }
    @Provides
    @Singleton
    public PodcastApplication provideAppContext(){
        return application;
    }
}
