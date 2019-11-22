package com.zawlynn.udacitycapstoneproject.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.zawlynn.udacitycapstoneproject.data.database.converter.PodcastTypeConverters;
import com.zawlynn.udacitycapstoneproject.data.database.dao.CuratedPodcastDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.EpisodeDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.GenreDao;
import com.zawlynn.udacitycapstoneproject.data.database.dao.PodcastDao;
import com.zawlynn.udacitycapstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;


@Database(entities = {Genre.class, Episode.class, Podcast.class, CuratedPodcast.class}, version = 5, exportSchema = false)
@TypeConverters({PodcastTypeConverters.class})
public abstract class PodcastDatabase extends RoomDatabase {
    public abstract GenreDao genreDao();
    public abstract EpisodeDao episodeDao();
    public abstract CuratedPodcastDao curatedPodcastDao();
    public abstract PodcastDao podcastDao();
}
