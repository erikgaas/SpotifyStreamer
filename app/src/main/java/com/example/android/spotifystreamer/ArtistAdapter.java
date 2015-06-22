package com.example.android.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by erik on 6/19/15.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {
    Context context;
    int layoutResourceId;
    Artist data[] = null;

    public ArtistAdapter(Context context, int layoutResourceId, Artist[] data) {
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
            holder.imgIcon = (ImageView)row.findViewById(R.id.artist_image);
            holder.txtTitle = (TextView)row.findViewById(R.id.artist_text);

            row.setTag(holder);
        }
        else {
            holder = (ArtistHolder)row.getTag();
        }
        Artist artist = data[position];
        holder.txtTitle.setText(artist.name);

        kaaes.spotify.webapi.android.models.Image[] artistImages = artist.images.toArray(new kaaes.spotify.webapi.android.models.Image[artist.images.size()]);
        if (artistImages.length != 0) {
            Picasso.with(context).load(artist.images.get(0).url).into(holder.imgIcon);
        } else {
            Picasso.with(context).load("http://simpleicon.com/wp-content/uploads/music-note-7.png").into(holder.imgIcon);
        }
        Log.v("BLABLALBLALBLA", artist.id);
        row.setTag(R.string.TAG_ID_ARTIST, artist.id);

        return row;
    }

    public String[] getArtistNames() {
        String[] artistNames = new String[data.length];
        for (int i = 0; i<data.length; i++) {
            artistNames[i] = data[i].name;
        }
        return artistNames;
    }

    public String[] getImageUrls() {
        String[] imageUrls = new String[data.length];
        for (int i = 0; i<data.length; i++) {
            if (data[i].images.size() > 0){
                imageUrls[i] = data[i].images.get(0).url;
            }
            else {
                imageUrls[i] = "http://simpleicon.com/wp-content/uploads/music-note-7.png";
            }
        }
        return imageUrls;
    }

    static class ArtistHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }

}