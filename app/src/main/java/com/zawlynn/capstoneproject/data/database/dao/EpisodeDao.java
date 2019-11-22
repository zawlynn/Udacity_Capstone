package com.zawlynn.capstoneproject.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zawlynn.capstoneproject.pojo.Episode;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public abstract class EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveEpisode(Episode data);

    @Query(" SELECT * FROM Episode where saved = 0 ORDER BY title DESC ")
    public abstract Flowable<List<Episode>> getAllEpisode();


    @Query(" SELECT * FROM Episode where saved = 1")
    public abstract Flowable<List<Episode>> getSavedEpisode();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveEpisodes(List<Episode> data);

    @Query("DELETE FROM Episode where id=:id")
    public abstract void removeEpisode(String id);

    @Query("SELECT id FROM Episode WHERE id = :id LIMIT 1")
    public abstract Observable<String> checkExists(String id);

}

