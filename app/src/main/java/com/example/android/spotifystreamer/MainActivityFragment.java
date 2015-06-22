package com.example.android.spotifystreamer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArtistAdapter mArtistAdapter;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        final SearchView searchView = (SearchView) rootView.findViewById(R.id.artist_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FetchArtistsTask artistsTask = new FetchArtistsTask();
                artistsTask.execute(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return rootView;
    }



    public class FetchArtistsTask extends AsyncTask<String, Void, Artist[]> {

        SpotifyApi api = new SpotifyApi();

        private final String LOG_TAG = FetchArtistsTask.class.getSimpleName();


        @Override
        protected Artist [] doInBackground(String... params) {
            String qString = params[0];
            try {
                SpotifyService spotify = api.getService();
                ArtistsPager results = spotify.searchArtists(qString);
                Artist[] artists = results.artists.items.toArray(new Artist[results.artists.items.size()]);
                return artists;
            } catch (Exception e) {
                return null;
            }


        }

        @Override
        protected void onPostExecute(Artist[] artists) {
            if (artists != null && artists.length != 0) {
                //Populate the adapter with the information
                mArtistAdapter = new ArtistAdapter(
                        getActivity(),
                        R.layout.artist_list_item,
                        artists);

                ListView listView = (ListView) getActivity().findViewById(R.id.listview_artists);
                listView.setAdapter(mArtistAdapter);

            } else {
                CharSequence text = "No matching artists. Try refining search.";
                Toast.makeText(getActivity(),text, Toast.LENGTH_LONG).show();
            }

            ListView listView = (ListView) getActivity().findViewById(R.id.listview_artists);
            if (listView != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String artistId = (String) view.getTag(R.string.TAG_ID_ARTIST);
                        Intent intent = new Intent(getActivity(), DetailActivity.class)
                                .putExtra(Intent.EXTRA_TEXT, artistId);
                        startActivity(intent);
                    }
                });
            }


        }
    }


}


