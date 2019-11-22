package com.zawlynn.udacitycapstoneproject.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Podcast implements Parcelable {
    private String listennotes_url;
    private String title;
    private String image;
    @PrimaryKey
    @NonNull
    private String id;
    private String thumbnail;
    private String publisher;
    private String description;
    private long total_episodes;
    public Podcast(){

    }
    protected Podcast(Parcel in) {
        listennotes_url = in.readString();
        title = in.readString();
        image = in.readString();
        id = in.readString();
        thumbnail = in.readString();
        publisher = in.readString();
        description = in.readString();
        total_episodes = in.readLong();
        title_original = in.readString();
    }

    public static final Creator<Podcast> CREATOR = new Creator<Podcast>() {
        @Override
        public Podcast createFromParcel(Parcel in) {
            return new Podcast(in);
        }

        @Override
        public Podcast[] newArray(int size) {
            return new Podcast[size];
        }
    };

    public String getTitle_original() {
        return title_original;
    }

    public void setTitle_original(String title_original) {
        this.title_original = title_original;
    }

    String title_original;
    public String getListennotes_url() {
        return listennotes_url;
    }

    public void setListennotes_url(String listennotes_url) {
        this.listennotes_url = listennotes_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Podcast podcast = (Podcast) o;
        return id.equals(podcast.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTotal_episodes() {
        return total_episodes;
    }

    public void setTotal_episodes(long total_episodes) {
        this.total_episodes = total_episodes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(listennotes_url);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(id);
        dest.writeString(thumbnail);
        dest.writeString(publisher);
        dest.writeString(description);
        dest.writeLong(total_episodes);
        dest.writeString(title_original);
    }
}
