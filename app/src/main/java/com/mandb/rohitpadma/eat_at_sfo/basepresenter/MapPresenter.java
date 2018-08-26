package com.mandb.rohitpadma.eat_at_sfo.basepresenter;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rohitpadma on 3/14/18.
 */

public interface MapPresenter {
    public void getCurrentLocation();
    public void fetchRestaurantLocations(String pageToken, String placeType, LatLng currentLocation);
}
