package com.mandb.rohitpadma.eat_at_sfo.basemodel;

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
    LatLng currentLocation;
    String token;
    int count=0;
    List<PlaceMarker> placeMarkerList=new ArrayList<>();


    public MapPresenterImpl(MapView mapView)
    {
        this.mapView=mapView;
        this._placeservice = (IPlaceApi) PlaceService.provideUserRestService();
        currentLocation= Utility.getcurrentlocation();
       // currentLocation=new LatLng(AppConfiguration.lat,AppConfiguration.lng);
    }

    @Override
    public void getCurrentLocation() {

          mapView.setCurrentLocation(currentLocation);
    }

    @Override
    public void fetchRestaurantLocations(String pageToken,final String placeType) {



        Observable<PlaceMarker> placeMarkerObservable =  _placeservice.fetchPlacesrx(String.valueOf(currentLocation.latitude)+","+String.valueOf(currentLocation.longitude),
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
                getPlaceMarker(placeMarker,placeType);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void fetchPlaceByType(String placeType) {


        mapView.startProgress();
        mapView.clearMap();
        count = 0;
        mapView.setCurrentLocation(currentLocation);
        fetchRestaurantLocations(AppConfiguration.pagetoken,placeType);

    }



    public void getPlaceMarker(PlaceMarker placeMarker,String ptype)
    {

      //  mapView.showClusters(placeMarker.getResults());
        mapView.setMarker(placeMarker.getResults());

        if(count<2){


            try {

                TimeUnit.SECONDS.sleep(2);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            fetchRestaurantLocations(token,ptype);

        }
        else
        {

            mapView.stopProgress();
        }

    }




}
