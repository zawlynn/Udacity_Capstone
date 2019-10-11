package com.zawlynn.udacitycapstoneproject.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Podcast {
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
}
