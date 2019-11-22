package com.zawlynn.udacitycapstoneproject.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zawlynn.udacitycapstoneproject.pojo.CuratedPodcast;
import com.zawlynn.udacitycapstoneproject.pojo.Episode;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class CuratedPodcastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveuratedPodcast(CuratedPodcast data);

    @Query(" SELECT * FROM CuratedPodcast ORDER BY title DESC")
    public abstract Flowable<List<CuratedPodcast>> getAllCuratedPodcast();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveCuratedPodcast(List<CuratedPodcast> data);
}

