package com.zawlynn.udacitycapstoneproject.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Genre implements Parcelable {
    private String parent_id;
    @PrimaryKey
    @NonNull
    private String id="0";
    private String name;
    private boolean selected;
    public Genre(){

    }
    protected Genre(Parcel in) {
        parent_id = in.readString();
        id = in.readString();
        name = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(parent_id);
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeByte((byte) (selected ? 1 : 0));
    }
}
