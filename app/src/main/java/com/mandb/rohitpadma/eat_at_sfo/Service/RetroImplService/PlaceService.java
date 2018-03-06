package com.mandb.rohitpadma.eat_at_sfo.Service.RetroImplService;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mandb.rohitpadma.eat_at_sfo.Constant.AppConfiguration;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static java.lang.String.format;

/**
 * Created by rohitpadma on 3/5/18.
 */

public class PlaceService {



    public static IPlaceApi createPlaceService(final String githubToken) {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(AppConfiguration.BASE_API_URL);

        if (!TextUtils.isEmpty(githubToken)) {

            OkHttpClient client =
                    new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {

                            Request request = chain.request();
                            Request newReq =
                                    request
                                            .newBuilder()
                                            .addHeader("Authorization", format("token %s", githubToken))
                                            .build();
                            return chain.proceed(newReq);
                        }
                    }).build();

            builder.client(client);
        }

        return builder.build().create(IPlaceApi.class);
    }
}
