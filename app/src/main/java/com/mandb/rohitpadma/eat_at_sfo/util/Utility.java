package com.mandb.rohitpadma.eat_at_sfo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mandb.rohitpadma.eat_at_sfo.App;
import com.mandb.rohitpadma.eat_at_sfo.MapsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rohitpadma on 3/5/18.
 */



public class Utility {

    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    public static LatLng getcurrentlocation()
    {
        LatLng currentlocation=null;

                // check if GPS enabled
                GPSTracker gpsTracker = new GPSTracker(App.getInstance().getApplicationContext());

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            currentlocation=new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude());

            Log.d("current location ",String.valueOf(currentlocation.latitude+" "+currentlocation.longitude));
        }

        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }

        return currentlocation;
    }

    public static String getdayname()
    {
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");

        return simpleDateformat.format(now).toLowerCase();

    }

}
