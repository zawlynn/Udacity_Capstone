package com.zawlynn.capstoneproject.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Episode implements Parcelable {
    private String title;
    private String audio_length;
    private String listennotes_url;
    private String audio;
    private String description;
    private String thumbnail;
    private String pub_date_ms;
    private String image;
    private Boolean saved=false;
    @PrimaryKey
    @NonNull
    private String id="0";
    private String local_file_cache;

    public Episode(){

    }

    protected Episode(Parcel in) {
        title = in.readString();
        audio_length = in.readString();
        listennotes_url = in.readString();
        audio = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        pub_date_ms = in.readString();
        image = in.readString();
        byte tmpSaved = in.readByte();
        saved = tmpSaved == 0 ? null : tmpSaved == 1;
        id = in.readString();
        local_file_cache = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(audio_length);
        dest.writeString(listennotes_url);
        dest.writeString(audio);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeString(pub_date_ms);
        dest.writeString(image);
        dest.writeByte((byte) (saved == null ? 0 : saved ? 1 : 2));
        dest.writeString(id);
        dest.writeString(local_file_cache);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

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

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getLocal_file_cache() {
        return local_file_cache;
    }

    public void setLocal_file_cache(String local_file_cache) {
        this.local_file_cache = local_file_cache;
    }
}
