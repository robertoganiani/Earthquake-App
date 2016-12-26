package com.example.android.earthquakeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthquakeAsyncTask earthquakeData = new EarthquakeAsyncTask();
        earthquakeData.execute(USGS_REQUEST_URL);


    }

    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> result) {
            ListView earthquakeListView = (ListView) findViewById(R.id.list_view);

            final EarthquakeAdapter adapter = new EarthquakeAdapter(EarthquakeActivity.this, result);
            earthquakeListView.setAdapter(adapter);

                 /*on list item click open the details page*/
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Earthquake earthquake = adapter.getItem(position);
                    Uri earthquakeUri = Uri.parse(earthquake.getmEarthquakeDetailsUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        }
    }

}
