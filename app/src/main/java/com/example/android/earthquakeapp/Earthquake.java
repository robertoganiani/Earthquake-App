package com.example.android.earthquakeapp;

/**
 * Created by Robert on 12/23/2016.
 */

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mEarthquakeDetailsUrl;


    public Earthquake(double mMagnitude, String mLocation, long mTimeInMilliseconds, String mEarthquakeDetailsUrl) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mTimeInMilliseconds = mTimeInMilliseconds;
        this.mEarthquakeDetailsUrl = mEarthquakeDetailsUrl;

    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getmEarthquakeDetailsUrl() {
        return mEarthquakeDetailsUrl;
    }

}
