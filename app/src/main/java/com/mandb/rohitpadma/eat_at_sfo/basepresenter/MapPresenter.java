package com.mandb.rohitpadma.eat_at_sfo.basepresenter;

/**
 * Created by rohitpadma on 3/14/18.
 */

public interface MapPresenter {


    public void getCurrentLocation();
    public void fetchRestaurantLocations(String pageToken,String placeType);


}
