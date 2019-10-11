package com.zawlynn.udacitycapstoneproject.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zawlynn.udacitycapstoneproject.pojo.Episode;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveEpisode(Episode data);

    @Query(" SELECT * FROM Episode ORDER BY title DESC")
    public abstract Flowable<List<Episode>> getAllEpisode();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveEpisodes(List<Episode> data);
}

