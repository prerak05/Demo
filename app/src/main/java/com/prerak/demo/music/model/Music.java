package com.prerak.demo.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emxcel on 9/1/18.
 */

public class Music implements Parcelable {

    private int id;
    private String name;
    private String url;
    private String image;


    public Music(int id, String name, String url, String image) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.image = image;
    }

    protected Music(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
        image = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.image);
    }
}
