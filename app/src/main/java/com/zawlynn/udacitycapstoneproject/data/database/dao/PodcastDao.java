package com.zawlynn.udacitycapstoneproject.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.zawlynn.udacitycapstoneproject.pojo.Genre;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.DELETE;

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

