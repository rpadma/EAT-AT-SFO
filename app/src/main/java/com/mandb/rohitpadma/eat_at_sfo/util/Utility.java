package com.mandb.rohitpadma.eat_at_sfo.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.mandb.rohitpadma.eat_at_sfo.App;

/**
 * Created by rohitpadma on 3/5/18.
 */



public class Utility {

    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
