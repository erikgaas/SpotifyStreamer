package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
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

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

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

    private TrackAdapter mTrackAdapter;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String artistId = intent.getStringExtra(Intent.EXTRA_TEXT);

            FetchTracksTask tracksTask = new FetchTracksTask();
            tracksTask.execute(artistId);
        }
        return rootView;
    }


    public class FetchTracksTask extends AsyncTask<String, Void, Track[]> {

        SpotifyApi api = new SpotifyApi();

        private final String LOG_TAG = FetchTracksTask.class.getSimpleName();


        @Override
        protected Track [] doInBackground(String... params) {
            String qString = params[0];
            Map country = new HashMap<String, Object>(1);
            country.put("country", "US");
            try {
                SpotifyService spotify = api.getService();
                Tracks results = spotify.getArtistTopTrack(qString, country);
                Track[] tracks = results.tracks.toArray(new Track[results.tracks.size()]);
                return tracks;
            } catch (Exception e) {
                return null;
            }


        }

        @Override
        protected void onPostExecute(Track[] tracks) {
            if (tracks != null && tracks.length != 0) {
                //Populate the adapter with the information
                mTrackAdapter = new TrackAdapter(
                        getActivity(),
                        R.layout.track_list_item,
                        tracks);

                ListView listView = (ListView) getActivity().findViewById(R.id.tracks_listview);
                listView.setAdapter(mTrackAdapter);

            } else {
                CharSequence text = "No matching tracks.";
                Toast.makeText(getActivity(),text, Toast.LENGTH_LONG).show();
            }


        }
    }
}
