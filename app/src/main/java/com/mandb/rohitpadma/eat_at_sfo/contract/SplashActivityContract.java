package com.mandb.rohitpadma.eat_at_sfo.contract;

import com.mandb.rohitpadma.eat_at_sfo.base.BasePresenter;
import com.mandb.rohitpadma.eat_at_sfo.base.BaseView;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.PlaceMarker;

import java.util.List;

/**
 * Created by rohitpadma on 3/6/18.
 */

public interface SplashActivityContract {
    interface View extends BaseView {

       // void showPlaceMarker(List<PlaceMarker> placeMarkerList);
        //void showNoNetworkMessage();

    }


    interface Presenter extends BasePresenter<View> {

        //void fetchNetwork(String type);

        //void checkNetworkAvailable();


    }
}

