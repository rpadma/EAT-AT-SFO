package com.mandb.rohitpadma.eat_at_sfo.basemodel;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.mandb.rohitpadma.eat_at_sfo.basepresenter.MapPresenter;
import com.mandb.rohitpadma.eat_at_sfo.baseview.MapView;
import com.mandb.rohitpadma.eat_at_sfo.baseview.RestaurantView;
import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.PlaceMarker;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Result;
import com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo.Restaurant;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.IPlaceApi;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.PlaceService;
import com.mandb.rohitpadma.eat_at_sfo.util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rohitpadma on 3/14/18.
 */

public class MapPresenterImpl implements MapPresenter{

    private IPlaceApi _placeservice;
    public MapView mapView;
    public LatLng currentLocation;
    String token;
    int count=0;
    List<PlaceMarker> placeMarkerList=new ArrayList<>();
    List<Result> results=new ArrayList<>();

    public MapPresenterImpl(MapView mapView) {
        this.mapView=mapView;
        this._placeservice = (IPlaceApi) PlaceService.provideUserRestService();
        currentLocation= Utility.getcurrentlocation();
    }


    @Override
    public void getCurrentLocation() {

          mapView.setCurrentLocation(currentLocation);
    }

    @Override
    public void fetchRestaurantLocations(String pageToken, final String placeType, final LatLng LcurrentLocation) {

        mapView.clearMap();
        Observable<PlaceMarker> placeMarkerObservable =  _placeservice.fetchPlacesrx(String.valueOf(LcurrentLocation.latitude)+","+String.valueOf(LcurrentLocation.longitude),
                AppConfiguration.radius,placeType,
                AppConfiguration.Key,
                pageToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        placeMarkerObservable.subscribeWith(new Observer<PlaceMarker>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PlaceMarker placeMarker) {

                count++;
                token=placeMarker.getNextPageToken();
                placeMarkerList.add(placeMarker);
                getPlaceMarker(placeMarker,placeType,LcurrentLocation);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void fetchPlaceByType(String placeType,LatLng customPosition) {

        Log.d("placetype",placeType);
        mapView.startProgress();
        mapView.clearMap();
        count = 0;
        mapView.setCurrentLocation(customPosition);
        fetchRestaurantLocations(AppConfiguration.pagetoken,placeType,customPosition);
    }

    public void getPlaceMarker(PlaceMarker placeMarker,String ptype,LatLng currentLocation) {
      //  mapView.showClusters(placeMarker.getResults());
        mapView.setMarker(placeMarker.getResults());
       // results.addAll(placeMarker.getResults());
        if(count<1){
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mapView.startProgress();
                }

            }, 2);
            fetchRestaurantLocations(token,ptype,currentLocation);
        }
        else {
          //  mapView.setPlaceView(results);
            mapView.stopProgress();
        }
    }
}
