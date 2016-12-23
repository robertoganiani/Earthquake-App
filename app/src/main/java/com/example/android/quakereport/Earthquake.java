package com.example.android.quakereport;

/**
 * Created by Robert on 12/23/2016.
 */

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private String mData;

    public Earthquake(double mMagnitude, String mLocation, String mData) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mData = mData;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmData() {
        return mData;
    }

}
