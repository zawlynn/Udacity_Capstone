package com.zawlynn.capstoneproject.di.module;

import android.app.Application;

import androidx.room.Room;

import com.zawlynn.capstoneproject.data.database.PodcastDatabase;
import com.zawlynn.capstoneproject.data.database.dao.CuratedPodcastDao;
import com.zawlynn.capstoneproject.data.database.dao.GenreDao;
import com.zawlynn.capstoneproject.data.database.dao.EpisodeDao;
import com.zawlynn.capstoneproject.data.database.dao.PodcastDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {
    @Provides
    GenreDao provideGenreDao(PodcastDatabase database){
        return database.genreDao();
    }
    @Provides
    EpisodeDao provideEpisodeDao(PodcastDatabase database){
        return database.episodeDao();
    }
    @Provides
    CuratedPodcastDao provideCuratedPodcastDao(PodcastDatabase database){
        return database.curatedPodcastDao();
    }
    @Provides
    PodcastDao providePodcastDao(PodcastDatabase database){
        return database.podcastDao();
    }
    @Provides
    @Singleton
    public PodcastDatabase provideTodoDatabase(Application application){
        return Room.databaseBuilder(application,PodcastDatabase.class,"podcast.db")
                .fallbackToDestructiveMigration().build();
    }
}
