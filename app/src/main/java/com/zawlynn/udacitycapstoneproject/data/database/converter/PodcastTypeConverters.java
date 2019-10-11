package com.zawlynn.udacitycapstoneproject.data.database.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zawlynn.udacitycapstoneproject.pojo.Podcast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PodcastTypeConverters {
    @TypeConverter
    public static ArrayList<Podcast> stringToContents(String value) {
        Type listType = new TypeToken<ArrayList<Podcast>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String contentToString(ArrayList<Podcast> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
