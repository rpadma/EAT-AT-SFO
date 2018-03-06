package com.mandb.rohitpadma.eat_at_sfo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    public PlaceMarker placeMarkerList;
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

       if(Utility.isNetworkConnected()) {

           fetchdata();
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

        LatLng sydney = new LatLng(AppConfiguration.lat, AppConfiguration.lng);
        mMap.addMarker(new MarkerOptions().position(sydney)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("your location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        b.include(sydney);

        mUiSettings = mMap.getUiSettings();
        mMap.getUiSettings().setZoomControlsEnabled(true);




    }

    public void showRestaurant(Result r)
    {
        Bundle b=new Bundle();
        b.putParcelable("marker",r);
        Intent i=new Intent(this,RestaurantActivity.class);
        i.putExtras(b);
        startActivity(i);
    }


    public void setMarker(PlaceMarker example)
    {
        for (Result res:example.getResults()) {

            LatLng temp=new LatLng(res.getGeometry().getLocation().getLat(),res.getGeometry().getLocation().getLng());

            if(res!=null && res.getOpeningHours()!=null && res.getOpeningHours().getOpenNow()) {

                MarkerOptions mo= new MarkerOptions()
                        .position(temp)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(res.getName()).snippet(res.getOpeningHours().getOpenNow()?"Opened":"Closed");
                final Marker m =
                        mMap.addMarker(mo);
                hmap.put(m, res);
            }
            else
            {
               MarkerOptions mo= new MarkerOptions()
                        .position(temp)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title(res.getName()).snippet(res.getOpeningHours().getOpenNow()?"Opened":"Closed");
                final Marker m =
                        mMap.addMarker(mo);

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


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Result r=hmap.get(marker);
                showRestaurant(r);
                return false;
            }
        });
    }


    public void fetchdata()
    {


        Call<PlaceMarker> call=_placeservice.fetchPlaces(String.valueOf(AppConfiguration.lat)+","+String.valueOf(AppConfiguration.lng),
                AppConfiguration.radius,AppConfiguration.placetype,AppConfiguration.Key);
        call.enqueue(new Callback<PlaceMarker>() {
            @Override
            public void onResponse(Call<PlaceMarker> call, Response<PlaceMarker> response) {

                placeMarkerList=response.body();

                setMarker(placeMarkerList);
            }

            @Override
            public void onFailure(Call<PlaceMarker> call, Throwable t) {

            }
        });

    }


}
