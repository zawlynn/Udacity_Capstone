package com.zawlynn.udacitycapstoneproject.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.zawlynn.udacitycapstoneproject.pojo.Genre;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveGenre(Genre data);

    @Query(" SELECT * FROM Genre ORDER BY name DESC")
    public abstract Flowable<List<Genre>> getAllGenres();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveGenres(List<Genre> dotoList);
}

