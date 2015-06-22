package com.example.android.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by erik on 6/21/15.
 */
public class TrackAdapter extends ArrayAdapter<ParcelTrack> {
    Context context;
    int layoutResourceId;
    ArrayList<ParcelTrack> data = null;

    public TrackAdapter(Context context, int layoutResourceId, ArrayList<ParcelTrack> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ArtistHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ArtistHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.track_image);
            holder.trackName = (TextView)row.findViewById(R.id.track_text);
            holder.artistName = (TextView)row.findViewById(R.id.artist_name);


            row.setTag(holder);
        }
        else {
            holder = (ArtistHolder)row.getTag();
        }
        ParcelTrack track = data.get(position);
        holder.trackName.setText(track.trackName);
        holder.artistName.setText(track.albumName);
        Picasso.with(context).load(track.imageUrl).into(holder.imgIcon);

/*        kaaes.spotify.webapi.android.models.Image[] trackImages = track.album.images.toArray(new kaaes.spotify.webapi.android.models.Image[track.album.images.size()]);
        if (trackImages.length != 0) {
            Picasso.with(context).load(trackImages[0].url).into(holder.imgIcon);
        } else {
            Picasso.with(context).load("http://simpleicon.com/wp-content/uploads/music-note-7.png").into(holder.imgIcon);
        }*/


        return row;
    }



    static class ArtistHolder
    {
        ImageView imgIcon;
        TextView trackName;
        TextView artistName;
    }

}
