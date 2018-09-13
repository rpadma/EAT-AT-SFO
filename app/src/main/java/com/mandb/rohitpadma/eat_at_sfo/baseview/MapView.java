package com.mandb.rohitpadma.eat_at_sfo.baseview;

import com.google.android.gms.maps.model.LatLng;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Result;

import java.util.List;

/**
 * Created by rohitpadma on 3/14/18.
 */

public interface MapView {

    public void setCurrentLocation(LatLng currentLocation);
    public void setMarker(List<Result> results);
    public void clearMap();
    public void showClusters(List<Result> results);
    public void startProgress();
    public void stopProgress();
    public void setPlaceView(List<Result> results);
}
