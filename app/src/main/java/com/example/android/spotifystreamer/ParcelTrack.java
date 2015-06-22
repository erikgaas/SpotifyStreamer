package com.example.android.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by erik on 6/22/15.
 */
public class ParcelTrack implements Parcelable {
    String imageUrl;
    String trackName;
    String albumName;

    public ParcelTrack(String imageUrl, String trackName, String artistName) {
        this.imageUrl = imageUrl;
        this.trackName = trackName;
        this.albumName = artistName;
    }

    private ParcelTrack(Parcel in) {
        imageUrl = in.readString();
        trackName = in.readString();
        albumName = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(trackName);
        dest.writeString(albumName);
    }

    public static final Parcelable.Creator<ParcelTrack> CREATOR = new Parcelable.Creator<ParcelTrack>() {
        @Override
        public ParcelTrack[] newArray(int size) {
            return new ParcelTrack[size];
        }

        @Override
        public ParcelTrack createFromParcel(Parcel source) {
            return new ParcelTrack(source);
        }
    };
}