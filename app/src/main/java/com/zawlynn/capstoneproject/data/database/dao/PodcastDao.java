package com.zawlynn.capstoneproject.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zawlynn.capstoneproject.pojo.Podcast;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public abstract class PodcastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void savePodcast(Podcast data);

    @Query(" SELECT * FROM Podcast")
    public abstract Flowable<List<Podcast>> getAllPodcast();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void savePodcast(List<Podcast> dotoList);

    @Query("SELECT id FROM Podcast WHERE id = :id LIMIT 1")
    public abstract Observable<String> checkExists(String id);

    @Query("DELETE FROM Podcast where id=:id")
    public abstract void deletePodcast(String id);

}

