package com.mandb.rohitpadma.eat_at_sfo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.PlaceMarker;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Result;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.IPlaceApi;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.PlaceService;
import com.mandb.rohitpadma.eat_at_sfo.util.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;

    private IPlaceApi _placeservice;
    private UiSettings mUiSettings;
    HashMap<Marker,Result> hmap=new HashMap<>();
    LatLng currentlocation=null;
    LatLngBounds.Builder b = new LatLngBounds.Builder();
    List<PlaceMarker> placeMarkerList=new ArrayList<>();
    List<Result> resultList=new ArrayList<>();
    int count=0;
    CompositeDisposable disposable;

    PlaceMarker placeMarker1;
    PlaceMarker placeMarker2;
    PlaceMarker getPlaceMarker3;
    String token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this);

        _placeservice = (IPlaceApi) PlaceService.provideUserRestService();

        disposable=new CompositeDisposable();
       currentlocation=Utility.getcurrentlocation();

       //Log.d("Currentlat",String.valueOf(currentlocation.latitude));
      // Log.d("Currentlong",String.valueOf(currentlocation.longitude));
      // currentlocation=new LatLng(35.3164989,-80.74309089999997);
        if(Utility.isNetworkConnected()) {

          // fetchdata(AppConfiguration.pagetoken);
           fetchplacemarker(AppConfiguration.pagetoken);

       }
       else
       {
           Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();

       }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        LatLng sydney = currentlocation; //new LatLng(AppConfiguration.lat, AppConfiguration.lng);

       Marker current= mMap.addMarker(new MarkerOptions().position(sydney)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("your location"));
       current.showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



        b.include(sydney);

        mUiSettings = mMap.getUiSettings();
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }



    public void showRestaurant(Result r)
    {
        if(r!=null) {
            Bundle b = new Bundle();
            b.putParcelable("marker", r);
            Intent i = new Intent(this, RestaurantActivity.class);
            i.putExtras(b);
            startActivity(i);
        }
    }


    public void setMarker(List<Result> results)
    {



        Log.d("Next token", String.valueOf(results.size()));
        for (Result res:results) {

            LatLng temp=new LatLng(res.getGeometry().getLocation().getLat(),res.getGeometry().getLocation().getLng());

            if(res!=null && res.getOpeningHours()!=null && res.getOpeningHours().getOpenNow()) {

                MarkerOptions mo= new MarkerOptions()
                        .position(temp)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(res.getName())
                        .snippet(res.getOpeningHours().getOpenNow()?"Opened":"Closed");
                   Marker m =
                        mMap.addMarker(mo);

                hmap.put(m, res);
            }
            else
            {
               MarkerOptions mo= new MarkerOptions()
                        .position(temp)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title(res.getName())
                       .snippet(res.getOpeningHours().getOpenNow()?"Opened":"Closed");
                Marker m =
                        mMap.addMarker(mo);


                hmap.put(m, res);

            }

            //if(count==1) {
                b.include(temp);
            //}
        }


            LatLngBounds bounds = b.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
//            CameraUpdate cu1 = CameraUpdateFactory.newLatLngBounds(bounds, width, height, 250);

            CameraUpdate cu= CameraUpdateFactory.zoomTo(13);
            mMap.animateCamera(cu,500,null);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(MapsActivity.this, "Infowindow clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Result r=hmap.get(marker);
                showRestaurant(r);
                return false;
            }
        });
    }


    public void fetchdata(String pagetoken)
    {



      //  Log.d("Token"+String.valueOf(count),pagetoken);
        Call<PlaceMarker> call=_placeservice.fetchPlaces(String.valueOf(currentlocation.latitude)+","+String.valueOf(currentlocation.longitude),
                AppConfiguration.radius,AppConfiguration.placetype,
              //  AppConfiguration.nextPage,

                //AppConfiguration.sensorvalue,
                AppConfiguration.Key,
                pagetoken);
        call.enqueue(new Callback<PlaceMarker>() {
            @Override
            public void onResponse(Call<PlaceMarker> call, Response<PlaceMarker> response) {

               PlaceMarker placeMarker=response.body();
                Log.d("Token"+String.valueOf(count),placeMarker.toString());

                resultList.addAll(placeMarker.getResults());
                count++;
                getPlaceMarker(placeMarker);

            }

            @Override
            public void onFailure(Call<PlaceMarker> call, Throwable t) {

            }
        });




    }


    public void fetchplacemarker(final String pagetoken)
    {

                Observable<PlaceMarker> placeMarkerObservable =  _placeservice.fetchPlacesrx(String.valueOf(currentlocation.latitude)+","+String.valueOf(currentlocation.longitude),
            AppConfiguration.radius,AppConfiguration.placetype,
            //  AppConfiguration.nextPage,

            //AppConfiguration.sensorvalue,
            AppConfiguration.Key,
            pagetoken)
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

                getPlaceMarker(placeMarker);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }




    public void getPlaceMarker(PlaceMarker placeMarker)
    {

        for(Result res:placeMarker.getResults())
        {
            Log.d("Res name",res.getName());
        }
        resultList.addAll(placeMarker.getResults());

            setMarker(placeMarker.getResults());


        if(token!=null)
        Log.d("Token",token);

        if(count<1){
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            fetchplacemarker(token);

        }




       // fetchdata1(placeMarker.getNextPageToken());

         //  fetchdata1(place.getNextPageToken());

    }

    /**
     * Destroy all fragments and loaders.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(disposable!=null)
        {
            disposable.dispose();
        }
    }


    @OnClick(R.id.frestaurant)
    public void onRestaurantClick()
    {
        Toast.makeText(MapsActivity.this,"On Restaurant Click",Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.fbakery)
    public void onBakeryClick()
    {
        Toast.makeText(MapsActivity.this,"On Bakery Click",Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.fcoffee)
    public void onCoffeeClick()
    {
        Toast.makeText(MapsActivity.this,"On Coffee Click",Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.fbar)
    public void onBarClick()
    {
        Toast.makeText(MapsActivity.this,"On Bar Click",Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.fwine)
    public void onWineClick()
    {

        Toast.makeText(MapsActivity.this,"On Wine Click",Toast.LENGTH_SHORT).show();
    }
}
