package com.zawlynn.udacitycapstoneproject.di.module;

import android.app.Application;

import androidx.room.Room;

import com.zawlynn.udacitycapstoneproject.data.database.PodcastDatabase;
import com.zawlynn.udacitycapstoneproject.data.database.dao.CuratedPodcastDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.EpisodeDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.GenreDao;

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
    @Singleton
    public PodcastDatabase provideTodoDatabase(Application application){
        return Room.databaseBuilder(application,PodcastDatabase.class,"podcast.db")
                .fallbackToDestructiveMigration().build();
    }
}
