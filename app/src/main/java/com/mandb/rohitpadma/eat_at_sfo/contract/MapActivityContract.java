package com.mandb.rohitpadma.eat_at_sfo.contract;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mandb.rohitpadma.eat_at_sfo.base.BasePresenter;
import com.mandb.rohitpadma.eat_at_sfo.base.BaseView;

/**
 * Created by rohitpadma on 3/6/18.
 */

public interface MapActivityContract {

    interface View extends BaseView {

        void showLocationPremissionDailog();
    }



    interface Presenter extends BasePresenter<View> {

        void setMap(GoogleMap map);

        GoogleMap getmMap();

        void drawMap();

//        void drawRoute(double latitude, double longitude, Marker marker);

    }


}
