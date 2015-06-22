package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    @InjectView(R.id.tracks_listview) ListView listView;

    private TrackAdapter mTrackAdapter;
    private ArrayList<ParcelTrack> parcelTracks;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, rootView);

        if (savedInstanceState == null || !savedInstanceState.containsKey("Tracks")) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                String artistId = intent.getStringExtra(Intent.EXTRA_TEXT);

                FetchTracksTask tracksTask = new FetchTracksTask();
                tracksTask.execute(artistId);
        }
            }
        else {
            Log.v("Sucess", "We did it!");
            parcelTracks = savedInstanceState.getParcelableArrayList("Tracks");
            mTrackAdapter = new TrackAdapter(
                    getActivity(),
                    R.layout.track_list_item,
                    parcelTracks);

            listView.setAdapter(mTrackAdapter);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Tracks", parcelTracks);
        super.onSaveInstanceState(outState);

    }



    public class FetchTracksTask extends AsyncTask<String, Void, ArrayList<ParcelTrack>> {

        SpotifyApi api = new SpotifyApi();

        private final String LOG_TAG = FetchTracksTask.class.getSimpleName();


        @Override
        protected ArrayList<ParcelTrack> doInBackground(String... params) {
            String qString = params[0];
            Map country = new HashMap<String, Object>(1);
            country.put("country", "US");
            try {
                SpotifyService spotify = api.getService();
                Tracks results = spotify.getArtistTopTrack(qString, country);
                Track[] tracks = results.tracks.toArray(new Track[results.tracks.size()]);
                parcelTracks = new ArrayList<ParcelTrack>();
                for (int i=0; i < tracks.length; i++) {
                    String trackImage;
                    if (tracks[i].album.images.size() != 0) {
                        trackImage = tracks[i].album.images.get(0).url;
                    }
                    else {
                        trackImage = "http://simpleicon.com/wp-content/uploads/music-note-7.png";
                    }
                    parcelTracks.add(new ParcelTrack(
                            trackImage,
                            tracks[i].name,
                            tracks[i].album.name));
                }
                return parcelTracks;
            } catch (Exception e) {
                return null;
            }


        }

        @Override
        protected void onPostExecute(ArrayList<ParcelTrack> parcelTracks) {
            if (parcelTracks != null && parcelTracks.size() != 0) {
                //Populate the adapter with the information
                mTrackAdapter = new TrackAdapter(
                        getActivity(),
                        R.layout.track_list_item,
                        parcelTracks);

                listView.setAdapter(mTrackAdapter);

            } else {
                CharSequence text = "No matching tracks.";
                Toast.makeText(getActivity(),text, Toast.LENGTH_LONG).show();
            }


        }
    }
}
