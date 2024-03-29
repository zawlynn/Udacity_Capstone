package com.zawlynn.capstoneproject.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.zawlynn.capstoneproject.data.database.dao.CuratedPodcastDao;
import com.zawlynn.capstoneproject.pojo.CuratedPodcast;
import com.zawlynn.capstoneproject.pojo.Genre;
import com.zawlynn.capstoneproject.pojo.Podcast;
import com.zawlynn.capstoneproject.data.database.converter.PodcastTypeConverters;
import com.zawlynn.capstoneproject.data.database.dao.EpisodeDao;
import com.zawlynn.capstoneproject.data.database.dao.GenreDao;
import com.zawlynn.capstoneproject.data.database.dao.PodcastDao;
import com.zawlynn.capstoneproject.pojo.Episode;


@Database(entities = {Genre.class, Episode.class, Podcast.class, CuratedPodcast.class}, version = 5, exportSchema = false)
@TypeConverters({PodcastTypeConverters.class})
public abstract class PodcastDatabase extends RoomDatabase {
    public abstract GenreDao genreDao();
    public abstract EpisodeDao episodeDao();
    public abstract CuratedPodcastDao curatedPodcastDao();
    public abstract PodcastDao podcastDao();
}
