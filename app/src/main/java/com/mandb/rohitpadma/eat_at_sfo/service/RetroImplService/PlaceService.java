package com.mandb.rohitpadma.eat_at_sfo.service.RetroImplService;

import com.mandb.rohitpadma.eat_at_sfo.constant.AppConfiguration;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static java.lang.String.format;

/**
 * Created by rohitpadma on 3/5/18.
 */

public class PlaceService {



    private static String BASE_API_URL = AppConfiguration.BASE_API_URL;
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofitInstance;
    private static IPlaceApi iPlaceApi;



    private static okhttp3.OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (IPlaceApi.class) {


                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

                /**
                 * this is for logging the request info need to set to only debug
                 */
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

                mOkHttpClient = new okhttp3.OkHttpClient.Builder()
                        //     .cache(cache)
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .addNetworkInterceptor(httpLoggingInterceptor)
                        .build();
            }
        }

        return mOkHttpClient;
    }

    private static retrofit2.Retrofit getRetrofitInstance() {
        if (mRetrofitInstance == null) {
            mRetrofitInstance = new retrofit2.Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    //  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofitInstance;
    }

    public static IPlaceApi provideUserRestService() {
        if (iPlaceApi == null) {
            iPlaceApi = getRetrofitInstance().create(IPlaceApi.class);
        }
        return iPlaceApi;
    }




}
