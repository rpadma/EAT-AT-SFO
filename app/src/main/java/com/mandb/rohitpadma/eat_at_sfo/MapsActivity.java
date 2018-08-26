package com.mandb.rohitpadma.eat_at_sfo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.mandb.rohitpadma.eat_at_sfo.basemodel.MapPresenterImpl;
import com.mandb.rohitpadma.eat_at_sfo.baseview.MapView;
import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Location;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.PlaceMarkerCluster;
import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Result;
import com.mandb.rohitpadma.eat_at_sfo.util.Utility;
import com.victor.loading.rotate.RotateLoading;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,MapView,ClusterManager.OnClusterItemClickListener<Result>,
        ClusterManager.OnClusterItemInfoWindowClickListener<Result>,
        GoogleMap.OnCameraMoveStartedListener {

    @BindView(R.id.rotateloading)
    RotateLoading progressloader;
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    HashMap<Marker,Result> hmap=new HashMap<>();
    MapPresenterImpl mapPresenter;
    private ClusterManager<Result> mClusterManager;
    Point screenPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this);
        screenPoint=new Point();
        screenPoint.x = this.getResources().getDisplayMetrics().widthPixels / 2;
        screenPoint.y = this.getResources().getDisplayMetrics().heightPixels / 2;
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
        mClusterManager = new ClusterManager<Result>(this, mMap);

        mapPresenter=new MapPresenterImpl(MapsActivity.this);
        mapPresenter.getCurrentLocation();

        CameraUpdate cu= CameraUpdateFactory.zoomTo(14);
        mMap.animateCamera(cu,500,null);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
     /*   mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Result r=hmap.get(marker);
                showRestaurant(r);
                //Toast.makeText(MapsActivity.this, "Infowindow clicked", Toast.LENGTH_SHORT).show();
            }
        });
        */
     mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        // Add cluster items (markers) to the cluster manager.

        mMap.setOnCameraMoveStartedListener(this);


    }




     @Override
    public void setCurrentLocation(LatLng currentLocation)
    {
       Marker current= mMap.addMarker(new MarkerOptions().position(currentLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
               .title("your location"));
      /*  Marker current= mMap.addMarker(new MarkerOptions().position(currentLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.down)).title("your location"));*/
        current.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mUiSettings = mMap.getUiSettings();
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void showRestaurant(Result r) {
        if(r!=null) {
            Bundle b = new Bundle();
            b.putParcelable("marker", r);
            Intent i = new Intent(this, RestaurantActivity.class);
            i.putExtras(b);
            startActivity(i);
        }
    }

     @Override
    public void setMarker(List<Result> results)
    {
     //   showClusters(results);
        Log.d("Tag",String.valueOf(results.size()));
        for (Result res:results) {

            LatLng temp=new LatLng(res.getGeometry().getLocation().getLat(),res.getGeometry().getLocation().getLng());


            if(res!=null && res.getOpeningHours()!=null &&  res.getOpeningHours().getOpenNow()!=null && res.getOpeningHours().getOpenNow()) {

                MarkerOptions mo= new MarkerOptions()
                        .position(temp)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title(res.getName())
                        .snippet(res.getOpeningHours().getOpenNow()?"Opened":"Closed");
                   Marker m =
                        mMap.addMarker(mo);



                hmap.put(m, res);
            }
            else if(res!=null && res.getOpeningHours()!=null &&  res.getOpeningHours().getOpenNow()!=null && !res.getOpeningHours().getOpenNow())
            {
               MarkerOptions mo= new MarkerOptions()
                        .position(temp)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title(res.getName())
                       .snippet(res.getOpeningHours().getOpenNow()?"Opened":"Closed");
                Marker m = mMap.addMarker(mo);
                hmap.put(m, res);
            }

        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Result r=hmap.get(marker);
                showRestaurant(r);
            }
        });
    }


    public void clearMap() {
        mMap.clear();
    }


    @Override
    public void showClusters(List<Result> results) {



        for (Result res:results) {

            mClusterManager.addItem(res);
        }
    }

    @Override
    public void startProgress() {

        progressloader.start();
    }

    @Override
    public void stopProgress() {

        progressloader.stop();
    }

    /**
     * Destroy all fragments and loaders.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    @OnClick(R.id.frestaurant)
    public void onRestaurantClick()
    {
        mapPresenter.fetchPlaceByType(AppConfiguration.placetype);
    }


    @OnClick(R.id.fbakery)
    public void onBakeryClick()
    {
        mapPresenter.fetchPlaceByType(AppConfiguration.bplacetype);
    }
    @OnClick(R.id.fcoffee)
    public void onCoffeeClick()
    {
        mapPresenter.fetchPlaceByType(AppConfiguration.cplacetype);
    }
    @OnClick(R.id.fbar)
    public void onBarClick()
    {
        mapPresenter.fetchPlaceByType(AppConfiguration.clubplacetype);
    }
    @OnClick(R.id.fwine)
    public void onWineClick()
    {
        mapPresenter.fetchPlaceByType(AppConfiguration.wplacetype);
    }



    @Override
    public boolean onClusterItemClick(Result result) {
        showRestaurant(result);
        return true;
    }

    @Override
    public void onClusterItemInfoWindowClick(Result result) {
        Log.d("results",result.getName());
        showRestaurant(result);

    }


    @Override
    public void onCameraMoveStarted(int i) {

        Projection myProjection = mMap.getProjection();
        LatLng markerPosition = myProjection.fromScreenLocation(screenPoint);

        if(Utility.isNetworkConnected()) {
            progressloader.start();
            mapPresenter.fetchRestaurantLocations(AppConfiguration.pagetoken,AppConfiguration.placetype,markerPosition);
        }
        else
        {
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }

       // Toast.makeText(getApplicationContext(),markerPosition.toString(),Toast.LENGTH_SHORT).show();
       // Log.d("screenpoint",markerPosition.toString());
       // Log.d("screenpoint",screenPoint.toString());

    }


}
