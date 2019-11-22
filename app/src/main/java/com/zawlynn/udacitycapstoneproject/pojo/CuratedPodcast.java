package com.zawlynn.udacitycapstoneproject.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.zawlynn.udacitycapstoneproject.data.database.converter.PodcastTypeConverters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class CuratedPodcast implements Parcelable {
    private long pub_date_ms;
    @TypeConverters(PodcastTypeConverters.class)
    private ArrayList<Podcast> podcasts;
    private String description;
    private String title;
    private String source_domain;
    @PrimaryKey
    @NonNull
    private String id;
    private String listennotes_url;
    private String source_url;

    public CuratedPodcast(){

    }
    protected CuratedPodcast(Parcel in) {
        pub_date_ms = in.readLong();
        podcasts = in.createTypedArrayList(Podcast.CREATOR);
        description = in.readString();
        title = in.readString();
        source_domain = in.readString();
        id = in.readString();
        listennotes_url = in.readString();
        source_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(pub_date_ms);
        dest.writeTypedList(podcasts);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(source_domain);
        dest.writeString(id);
        dest.writeString(listennotes_url);
        dest.writeString(source_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CuratedPodcast> CREATOR = new Creator<CuratedPodcast>() {
        @Override
        public CuratedPodcast createFromParcel(Parcel in) {
            return new CuratedPodcast(in);
        }

        @Override
        public CuratedPodcast[] newArray(int size) {
            return new CuratedPodcast[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuratedPodcast that = (CuratedPodcast) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public long getPub_date_ms() {
        return pub_date_ms;
    }

    public void setPub_date_ms(long pub_date_ms) {
        this.pub_date_ms = pub_date_ms;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource_domain() {
        return source_domain;
    }

    public void setSource_domain(String source_domain) {
        this.source_domain = source_domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListennotes_url() {
        return listennotes_url;
    }

    public void setListennotes_url(String listennotes_url) {
        this.listennotes_url = listennotes_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }
}
