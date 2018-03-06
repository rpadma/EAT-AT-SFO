package com.mandb.rohitpadma.eat_at_sfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.mandb.rohitpadma.eat_at_sfo.R;
import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;
import com.mandb.rohitpadma.eat_at_sfo.model.Example;
import com.mandb.rohitpadma.eat_at_sfo.model.Location;
import com.mandb.rohitpadma.eat_at_sfo.model.PlaceMarker;
import com.mandb.rohitpadma.eat_at_sfo.model.Result;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.IPlaceApi;
import com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService.PlaceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    public Example placeMarkerList;
    private IPlaceApi _placeservice;
    private UiSettings mUiSettings;
    HashMap<Marker,Result> hmap=new HashMap<>();

    LatLngBounds.Builder b = new LatLngBounds.Builder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        _placeservice = (IPlaceApi) PlaceService.provideUserRestService();


        fetchdata();
    }

    public void fetchdata()
    {


        Call<Example> call=_placeservice.fetchPlaces(String.valueOf(AppConfiguration.lat)+","+String.valueOf(AppConfiguration.lng),
                "5000","restaurant","AIzaSyBcLZtBC-2jSDG8iiZpECV_yWpUqaGeDTk");
call.enqueue(new Callback<Example>() {
    @Override
    public void onResponse(Call<Example> call, Response<Example> response) {

        placeMarkerList=response.body();

        setMarker(placeMarkerList);
    }

    @Override
    public void onFailure(Call<Example> call, Throwable t) {

    }
});

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


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(AppConfiguration.lat, AppConfiguration.lng);

        mMap.addMarker(new MarkerOptions().position(sydney).title("your location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        b.include(sydney);

        mUiSettings = mMap.getUiSettings();
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                Result r=hmap.get(marker);

                ShowRest(r);
                // Toast.makeText(MapsActivity.this,r.getName()+" "+r.getPlaceId(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }

    public void ShowRest(Result r)
    {
        Bundle b=new Bundle();
        b.putParcelable("marker",r);
        Intent i=new Intent(this,RestaurantActivity.class);
        i.putExtras(b);
        startActivity(i);
    }


    public void setMarker(Example example)
    {
        for (Result res:example.getResults()) {

            LatLng temp=new LatLng(res.getGeometry().getLocation().getLat(),res.getGeometry().getLocation().getLng());


           final Marker m=mMap.addMarker(
                    new MarkerOptions()
                            .position(temp)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .title(res.getName()).snippet("fhfhhfdfnnjrnjfrrmjttktkttttm")


            );

           if(m!=null && res!=null) {
               hmap.put(m, res);
           }
            b.include(temp);




        }

        LatLngBounds bounds = b.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, 250);
        mMap.animateCamera(cu);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
    }





}
