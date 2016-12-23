package com.example.android.quakereport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        earthquakes.add(new Earthquake(7.5, "San Francisco", "Feb 2, 2016"));
        earthquakes.add(new Earthquake(5.5, "London", "Jul 12, 1995"));
        earthquakes.add(new Earthquake(4.5, "Tokyo", "Jun 4, 2005"));
        earthquakes.add(new Earthquake(2.5, "Mexico City", "Mar 7, 2008"));
        earthquakes.add(new Earthquake(5.6, "Moscow", "Oct 30, 2007"));
        earthquakes.add(new Earthquake(3.5, "Rio de Janeiro", "Sep 24, 2003"));
        earthquakes.add(new Earthquake(4.7, "Paris", "Fab 7, 1998"));

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}
