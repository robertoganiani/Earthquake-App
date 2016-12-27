package com.example.android.earthquakeapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private EarthquakeAdapter mAdapter;

    private TextView emptyTextView;
    private ProgressBar progressBar;

    private static final String USGS_REQUEST_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        ListView earthquakeListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(mAdapter);

        /*set progress bar while data loading*/
        progressBar = (ProgressBar) findViewById(R.id.earthquake_progress_bar);

        /*set empty view if there is no data*/
        emptyTextView = (TextView) findViewById(R.id.no_data_text_view);
        earthquakeListView.setEmptyView(emptyTextView);

        /*set click listener*/
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Earthquake currentEarthquake = mAdapter.getItem(position);

                Uri earthquakeUri = Uri.parse(currentEarthquake.getmEarthquakeDetailsUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {

            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet);
        }

    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String minMagnitude = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default)
        );

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "30");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        progressBar.setVisibility(View.GONE);

        mAdapter.clear();

        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }

        emptyTextView.setText(R.string.no_data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        mAdapter.clear();
    }

}
