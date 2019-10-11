package com.zawlynn.udacitycapstoneproject.pojo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Episode {
    private String title;
    private String audio_length;
    private String listennotes_url;
    private String audio;
    private String description;
    private String thumbnail;
    private String pub_date_ms;
    private String image;
    @PrimaryKey
    @NonNull
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudio_length() {
        return audio_length;
    }

    public void setAudio_length(String audio_length) {
        this.audio_length = audio_length;
    }

    public String getListennotes_url() {
        return listennotes_url;
    }

    public void setListennotes_url(String listennotes_url) {
        this.listennotes_url = listennotes_url;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPub_date_ms() {
        return pub_date_ms;
    }

    public void setPub_date_ms(String pub_date_ms) {
        this.pub_date_ms = pub_date_ms;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Episode episode = (Episode) o;
        return id.equals(episode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
