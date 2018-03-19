package com.mandb.rohitpadma.eat_at_sfo.model.markerpojo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by rohitpadma on 3/17/18.
 */

public class PlaceMarkerCluster implements ClusterItem {

    private  LatLng mPosition;
    private  String mTitle;
    private  String mSnippet;

    public PlaceMarkerCluster(double lat, double lng, String mTitle, String mSnippet) {
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
        mPosition = new LatLng(lat, lng);
    }

    public PlaceMarkerCluster(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
