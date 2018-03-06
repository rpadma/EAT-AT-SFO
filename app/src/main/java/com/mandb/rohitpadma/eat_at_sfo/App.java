package com.mandb.rohitpadma.eat_at_sfo;

import android.app.Application;

/**
 * Created by rohitpadma on 3/5/18.
 */

public class App extends Application {


    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static synchronized App getInstance(){
        return instance;
    }
}
